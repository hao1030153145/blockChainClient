package com.smartdatachain.api.web.controller;

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.SystemCode;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.biz.service.WalletService;
import com.smartdatachain.api.integration.bo.WalletBO;
import com.smartdatachain.api.integration.bo.WalletDealDetailBO;
import com.smartdatachain.api.util.BaiBeiWalletUtils;
import com.smartdatachain.api.util.eth.contract.ContractUtils;
import com.smartdatachain.api.util.eth.contract.TransferUtils;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.smartdatachain.api.web.po.CommonPO;
import com.smartdatachain.api.web.po.WalletDealDetailPO;
import com.smartdatachain.api.web.wallet.BaibeiWallet;
import com.smartdatachain.api.web.wallet.WalletManager;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.web.controller
 * 源文件:TaskSchedulingController.java
 * 钱包接口
 *
 * @author haolen ,Inc. All rights reserved.2018年10月31日
 */

@Controller("walletController")
@Api(value = "钱包", description = "钱包相关接口", position = 2)
@RequestMapping("/wallet")
public class WalletController {

    @Resource
    private WalletService walletService;


    @RequestMapping(value = "/getAddressAndMnemonicByPrivateKey.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据导入的私钥获得地址和助记词 ", notes = "", position = 0)
    public WalletBO getAddressAndMnemonicByPrivateKey(@RequestParam(value = "privateKey", required = true) @ApiParam
            (value = "钱包私钥", required = true) String privateKey,
                                                      HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        WalletBO walletBO = new WalletBO();
        if (Validate.isEmpty(privateKey) && Validate.isEmpty(privateKey)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("privateKey", privateKey);
        walletBO = walletService.getWalletByPrivate(param);
        // 如果数据库没有查询到对应的数据，就需要根据传过来的值，进行钱包的生成
        if (walletBO == null) {
            try {
                WalletBO walletBO1 = new WalletBO();
                BaibeiWallet wallet = BaiBeiWalletUtils.generateBip44WalletByPrivateKey(privateKey, "");
                WalletManager.getInstance().addWallet(wallet);
                List<Bip39Wallet> mListWallet = new ArrayList<>();
                mListWallet.add(wallet);
                walletBO1.setWalletMnemonic(wallet.getMnemonic());
                //将获得的公钥进行16进制转换获得最终公钥
                String publicKey = wallet.getKeyPair().getPublicKey().toString(16);
                walletBO1.setWalletPublicKey(publicKey);
                walletBO1.setWalletAddress("0x" + wallet.getWalletFile().getAddress());
                walletBO1.setWalletPrivateKey(privateKey);
                return walletBO1;
            } catch (CipherException | IOException e) {
                e.printStackTrace();
            }
        }
        return walletBO;
    }

    @RequestMapping(value = "/createWallet.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建钱包 ", notes = "", position = 0)
    public WalletBO createWallet(@RequestParam(value = "walletName", required = true) @ApiParam(value = "钱包名字",
            required = true) String walletName,
                                 @RequestParam(value = "walletPassword", required = true) @ApiParam(value = "钱包名称",
                                         required = true) String walletPassword,
                                 HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        WalletBO walletBO = new WalletBO();
        if (Validate.isEmpty(walletName) && Validate.isEmpty(walletName)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        List<Bip39Wallet> mListWallet = new ArrayList<>();
        walletBO.setWalletName(walletName);
        walletBO.setWalletPassword(walletPassword);
        walletBO.setWalletCredit((float) 0.000);

        // 下面生成钱包地址,助记词，公钥，密钥
        BaibeiWallet wallet = null;
        try {
            wallet = BaiBeiWalletUtils.generateBip44Wallet("");
            WalletManager.getInstance().addWallet(wallet);
            mListWallet.add(wallet);
            walletBO.setWalletAddress("0x" + wallet.getWalletFile().getAddress());
            walletBO.setWalletMnemonic(wallet.getMnemonic());
            //将获得的公钥进行16进制转换获得最终公钥
            String publicKey = wallet.getKeyPair().getPublicKey().toString(16);
            walletBO.setWalletPublicKey(publicKey);
            // 将获得的密钥在进行16进制转换获得最终的私钥
            String privateKey = wallet.getKeyPair().getPrivateKey().toString(16);
            walletBO.setWalletPrivateKey(privateKey);
        } catch (CipherException | IOException e) {
            e.printStackTrace();
        }
        int createResultInt = walletService.createWallet(walletBO);
        if (createResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        return walletBO;
    }

//    @RequestMapping(value = "/updateWalletPasswordByAddress.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "根据钱包地址修改钱包密码，本地修改密码 ", notes = "", position = 0)
//    public CommonPO updateWalletPasswordByAddress(@RequestParam(value = "walletAddress", required = true) @ApiParam
// (value = "钱包名称", required = true) String walletAddress,
//                                                  @RequestParam(value = "walletPassword", required = true)
// @ApiParam(value = "钱包密码", required = true) String walletPassword,
//                                                  HttpServletRequest req, HttpServletResponse res) {
//
//        Map<String, Object> param = new HashMap<>();
//
//        if (Validate.isEmpty(walletAddress)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        if (Validate.isEmpty(walletPassword)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        param.put("walletAddress", walletAddress);
//        param.put("walletPassword", walletPassword);
//        int updateWalletPasswordByAddressResultInt = walletService.updateWalletPasswordByAddress(param);
//
//        if (updateWalletPasswordByAddressResultInt < 0) {
//            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
//        }
//        CommonPO commonPO = new CommonPO();
//        commonPO.setCode(0);
//        return commonPO;
//    }

//    @RequestMapping(value = "/getMnemonicByNameAndAddress.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "根据钱包名字和地址获得助记词 ", notes = "", position = 0)
//    public WalletPO getMnemonicByNameAndAddress(@RequestParam(value = "walletAddress", required = true) @ApiParam
// (value = "钱包名称", required = true) String walletAddress,
//                                              @RequestParam(value = "walletName", required = true) @ApiParam(value
// = "钱包密码", required = true) String walletName,
//                                              HttpServletRequest req, HttpServletResponse res) {
//
//        Map<String, Object> param = new HashMap<>();
//
//        if (Validate.isEmpty(walletAddress)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        if (Validate.isEmpty(walletName)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        param.put("walletAddress", walletAddress);
//        param.put("walletName", walletName);
//        WalletPO walletPO = walletService.getMnemonicByNameAndAddress(param);
//
//        if (walletPO == null) {
//            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
//        }
//        return walletPO;
//    }
//
//    @RequestMapping(value = "/getWalletKeyByNameAndAddress.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "根据钱包地址和名字获取公钥和密钥 ", notes = "", position = 0)
//    public WalletPO getWalletKeyByNameAndAddress(@RequestParam(value = "walletAddress", required = true) @ApiParam
// (value = "钱包名称", required = true) String walletAddress,
//                                                 @RequestParam(value = "walletName", required = true) @ApiParam
// (value = "钱包密码", required = true) String walletName,
//                                                 HttpServletRequest req, HttpServletResponse res) {
//        Map<String, Object> param = new HashMap<>();
//        if (Validate.isEmpty(walletAddress)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        if (Validate.isEmpty(walletName)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        param.put("walletAddress", walletAddress);
//        param.put("walletName", walletName);
//
//        WalletPO walletPO = walletService.getWalletKeyByNameAndAddress(param);
//
//        if (walletPO == null) {
//            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
//        }
//        return walletPO;
//    }
//
//    @RequestMapping(value = "/deleteWalletByAddressAndPwd.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "根据钱包密码和钱包地址删除钱包 ", notes = "", position = 0)
//    public CommonPO deleteWalletByAddressAndPwd(@RequestParam(value = "walletAddress", required = true) @ApiParam
// (value = "钱包名称", required = true) String walletAddress,
//                                                @RequestParam(value = "walletPassword", required = true) @ApiParam
// (value = "钱包密码", required = true) String walletPassword,
//                                                HttpServletRequest req, HttpServletResponse res) {
//
//        Map<String, Object> param = new HashMap<>();
//        if (Validate.isEmpty(walletAddress)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        if (Validate.isEmpty(walletPassword)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        param.put("walletAddress", walletAddress);
//        param.put("walletPassword", walletPassword);
//        int updateWalletPasswordByAddressResultInt = walletService.deleteWalletByAddressAndPwd(param);
//
//        if (updateWalletPasswordByAddressResultInt < 0) {
//            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
//        }
//        CommonPO commonPO = new CommonPO();
//        commonPO.setCode(0);
//        return commonPO;
//    }
//
//
//    @RequestMapping(value = "/getWalletDetailByNameAndPwd.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "根据钱包名字和钱包密码获得钱包信息 ", notes = "", position = 0)
//    public WalletPO getWalletDetailByNameAndPwd(@RequestParam(value = "walletName", required = true) @ApiParam
// (value = "钱包名称", required = true) String walletName,
//                                                @RequestParam(value = "walletPassword", required = true) @ApiParam
// (value = "钱包密码", required = true) String walletPassword,
//                                                HttpServletRequest req, HttpServletResponse res) {
//
//        Map<String, Object> param = new HashMap<>();
//
//        if (Validate.isEmpty(walletName)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        if (Validate.isEmpty(walletPassword)) {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//        param.put("walletName", walletName);
//        param.put("walletPassword", walletPassword);
//        WalletPO walletPO = walletService.getWalletDetailByNameAndPwd(param);
//
//        if (walletPO == null) {
//            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
//        }
//        return walletPO;
//    }

    @RequestMapping(value = "/getWalletDealDetailByAddress.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据钱包地址获得钱包交易信息 ", notes = "", position = 0)
    public List<WalletDealDetailPO> getWalletDealDetailByAddress(@RequestParam(value = "walletAddress", required = true) @ApiParam(value = "钱包地址", required = true) String walletAddress,
                                                                 @RequestParam(value = "transType", required = true) @ApiParam(value = "转账类型", required = true) String transType,
                                                                 @RequestParam(value = "size", required = false) @ApiParam(value = "查询记录数 默认 10 条", required = false) String size,
                                                                 @RequestParam(value = "page", required = false) @ApiParam(value = "查询页数 默认第 1 页", required = false) String page,
                                                                 HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();

        if (Validate.isEmpty(walletAddress) || Validate.isEmpty(transType)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("walletAddress", walletAddress);
        param.put("transType", transType);
        int sizeInt = 10;
        if (!Validate.isEmpty(size) && size.matches("\\d+")) {
            sizeInt = Integer.parseInt(size);
        }
        int pageInt = 0;
        if (!Validate.isEmpty(page) && page.matches("\\d+")) {
            pageInt = Integer.parseInt(page);
            pageInt = (pageInt - 1) * sizeInt;
        }
        param.put("size", sizeInt);
        param.put("page", pageInt);
        List<WalletDealDetailPO> walletDealDetailPO = walletService.getWalletDealDetailByAddress(param);

        if (walletDealDetailPO == null) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        return walletDealDetailPO;
    }

    @RequestMapping(value = "/tranferToken.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "转账 ", notes = "", position = 0)
    public CommonPO tranferToken(
            @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "地址", required = true) String walletAddress,
            @RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
            @RequestParam(value = "transferAmount", required = true) @ApiParam(value = "'转账金额'", required = true) String transferAmount,
            @RequestParam(value = "toAddress", required = true) @ApiParam(value = "'转入钱包地址'", required = true) String toAddress,
            @RequestParam(value = "gasFee", required = true) @ApiParam(value = "'旷工费'", required = true) String gasFee,
            @RequestParam(value = "DealNote", required = false) @ApiParam(value = "'交易备注'", required = false) String DealNote,
            @RequestParam(value = "transType", required = true) @ApiParam(value = "转账类型 ETH 和 ZSL", required = true) String transType,
            HttpServletRequest req, HttpServletResponse res) {

        if (Validate.isEmpty(walletAddress) && Validate.isEmpty(walletPrivateKey) && Validate.isEmpty(transferAmount)
                && Validate.isEmpty(toAddress) && Validate.isEmpty(gasFee)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }

        Map<String, BigInteger> gasFeeETH = new HashMap<>();
        Map<String, BigInteger> gasFeeZSL = new HashMap<>();
        if ("ETH".equals(transType)) {
            gasFeeETH = TransferUtils.covertETHGasFee(gasFee);
        } else {
            gasFeeZSL = TransferUtils.covertZSLGasFee(gasFee);
        }
        try {
            if ("ETH".equals(transType)) {
                //ContractUtils.transferETHByUser(walletPrivateKey, toAddress, transferAmount);   // ETH转账
                TransferUtils.transferEthFroGasFee(walletPrivateKey, toAddress, transferAmount, gasFeeETH.get("gasPrice"), gasFeeETH.get("gasLimit")); // 矿工费转账
            } else {
                //ContractUtils.transferByUser(walletPrivateKey, toAddress, transferAmount);
                TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, transferAmount, gasFeeZSL.get("gasPrice"), gasFeeZSL.get("gasLimit"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtil.errorTrace("转账异常详细》》》》", e);
            throw new WebException(MySystemCode.WALLET_EXCEPTION_TRANSFER_ERROR);
        }

        // 组装转账数据
        WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
        walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
        walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
        walletDealDetailBO.setTransferToken(transferAmount);
        walletDealDetailBO.setWalletIntoAddress(toAddress);
        walletDealDetailBO.setWalletOutAddress(walletAddress);
        walletDealDetailBO.setTransType(transType);

        // 将转账记录存入数据库
        int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
        if (createWalletDealDetailResultInt < 0) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }


    @RequestMapping(value = "/getBalance.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获得余额 ", notes = "", position = 0)
    public Map getZSLBalance(@RequestParam(value = "walletAddress", required = true) @ApiParam(value = "地址", required = true) String walletAddress,
                             HttpServletRequest req, HttpServletResponse res) {

        if (Validate.isEmpty(walletAddress)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }

        String balanceETH = "0";
        String balanceZSL = "0";
        try {
            balanceZSL = ContractUtils.balanceOf(walletAddress);
            balanceETH = ContractUtils.balanceOfETH(walletAddress);
        } catch (Exception e) {
            throw new WebException(MySystemCode.WALLET_EXCEPTION_GETBALANCE_ERROR);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("balanceETH", balanceETH);
        resultMap.put("balanceZSL", balanceZSL);
        return resultMap;
    }

    @RequestMapping(value = "/getGasFee.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获得参考手续费 ", notes = "", position = 0)
    public Map getZSLBalance(HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            String gasPriceStr = TransferUtils.getCurrentGasFee().toString();
            BigDecimal gasPrice = new BigDecimal(gasPriceStr);
            BigDecimal gasLimit = new BigDecimal("21000");
            BigDecimal gasFee = gasPrice.multiply(gasLimit);
            BigDecimal gasD = new BigDecimal("10").pow(18);
            BigDecimal gasFeeResult = gasFee.divide(gasD);
            resultMap.put("code", 0);
            resultMap.put("gasFee", gasFeeResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    @RequestMapping(value = "/testTransNonce.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "测试交易nonce的值 ", notes = "", position = 0)
    public Map testTransNonce(@RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
                              @RequestParam(value = "transferAmount", required = true) @ApiParam(value = "'转账金额'", required = true) String transferAmount,
                              @RequestParam(value = "toAddress", required = true) @ApiParam(value = "'转入钱包地址'", required = true) String toAddress,
                              @RequestParam(value = "gasFee", required = true) @ApiParam(value = "'旷工费'", required = true) String gasFee,
                              HttpServletRequest req, HttpServletResponse res) {

        Map<String,Object> resultMap = new HashMap<>();

        Map<String,BigInteger> gasFeeZSL =  TransferUtils.covertZSLGasFee(gasFee);

        try {
            TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, transferAmount, gasFeeZSL.get("gasPrice"), gasFeeZSL.get("gasLimit"));
            TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, transferAmount, gasFeeZSL.get("gasPrice"), gasFeeZSL.get("gasLimit"));
        }catch (Exception e){
            e.printStackTrace();
        }

        resultMap.put("param","测试成功");
        return resultMap;
    }

}
