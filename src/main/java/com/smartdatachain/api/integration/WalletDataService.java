package com.smartdatachain.api.integration;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.integration.DataService;
import com.smartdatachain.api.integration.bo.WalletBO;
import com.smartdatachain.api.integration.bo.WalletDealDetailBO;
import com.smartdatachain.api.web.po.WalletDealDetailPO;
import com.smartdatachain.api.web.po.WalletPO;

import java.util.List;
import java.util.Map;

/**
 * 钱包访问接口
 *
 * @author haolen
 * @version 1.0
 * @see
 */
public interface WalletDataService extends DataService {

    /**
     * 获得钱包详细信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    WalletPO getWalletDetailByNameAndPwd(Map<String, Object> param) throws BizException;

    /**
     * 根据钱包私钥获得钱包信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    WalletBO getWalletByPrivate(Map<String, Object> param) throws BizException;

    /**
     * 创建钱包
     *
     * @param walletBO
     * @return
     * @throws BizException
     */
    Integer createWallet(WalletBO walletBO) throws BizException;

    /**
     * 根据钱包地址修改钱包密码
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer updateWalletPasswordByAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据钱包名字和地址获得钱包助记词
     *
     * @param param
     * @return
     * @throws BizException
     */
    WalletPO getMnemonicByPwdAndAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据钱包名字和地址获得公钥密钥
     *
     * @param param
     * @return
     * @throws BizException
     */
    WalletPO getWalletKeyByPwdAndAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据钱包地址和密码删除钱包
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer deleteWalletByAddressAndPwd(Map<String, Object> param) throws BizException;


    /**
     * 根据钱包id获得钱包交易信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    List<WalletDealDetailPO> getWalletDealDetailByAddress(Map<String, Object> param) throws BizException;

    /**
     * 创建转账交易信息
     *
     * @param walletDealDetailBO
     * @return
     * @throws BizException
     */
    Integer createWalletDealDetail(WalletDealDetailBO walletDealDetailBO) throws BizException;


    /**
     * 根据钱包地址修改钱包信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer updateWalletCreditByAddress(Map<String, Object> param) throws BizException;

    /**
     * 根据地址和密码获取钱包信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    WalletPO getWalletDetailByAddressAndPwd(Map<String, Object> param) throws BizException;

}