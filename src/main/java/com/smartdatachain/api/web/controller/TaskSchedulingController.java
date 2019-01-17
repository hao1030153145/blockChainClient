
package com.smartdatachain.api.web.controller;

import com.jeeframework.jeetask.startup.JeeTaskClient;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.biz.service.WalletService;
import com.smartdatachain.api.integration.bo.WalletDealDetailBO;
import com.smartdatachain.api.util.eth.contract.ContractUtils;
import com.smartdatachain.api.util.eth.contract.TransferUtils;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.smartdatachain.job.JeeTask;
import com.smartdatachain.job.TaskFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


/**
 * 包: com.smartdatachain.api.web.controller
 * 源文件:TaskSchedulingController.java
 * 任务调度
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */

@RequestMapping(value = "/task")
@Controller
@Api(value = "任务调度")
public class TaskSchedulingController {
    @Resource
    private JeeTaskClient jeeTaskClient;
    @Resource
    private WalletService walletService;

    @RequestMapping(value = "/getTask.json", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取任务")
    public Map keepConnection(@RequestParam(value = "clientMK", required = true) @ApiParam(value = "客户端标示", required = true) String clientMK) throws Exception {
        TaskFactory taskFactory = TaskFactory.getInstance();
        JeeTask task = taskFactory.getTaskINFO();
        Map map = new HashMap();
        if (task != null) {
            map.put("task", task.getParam());
        } else {
            map.put("task", "");
        }
        System.out.println("获取任务》》》》》客户端标示为：" + clientMK);

        return map;
    }

    @RequestMapping(value = "/submitTask.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "提交任务")
    public Map submitTask(@RequestParam(value = "param", required = true) @ApiParam(value = "任务参数", required = true) String param,
                          @RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
                          @RequestParam(value = "payNum", required = true) @ApiParam(value = "金额", required = true) String payNum,
                          @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "地址", required = true) String walletAddress,
                          @RequestParam(value = "gasFee", required = true) @ApiParam(value = "矿工费", required = true) String gasFee) throws Exception {

        System.out.println("提交任务》》》》任务参数：" + param);
        System.out.println("提交任务》》》》私钥：" + walletPrivateKey);
        System.out.println("提交任务》》》》金额：" + payNum);
        System.out.println("提交任务》》》》地址：" + walletAddress);
        System.out.println("提交任务》》》》矿工费：" + gasFee);

        //支付
        String toAddress = ContractUtils.CONTRACT_ADDRESS; // 获得中心者的 钱包地址
        // 为静态变量进行赋值
        ContractUtils.GAS_FEE = gasFee;
        ContractUtils.PAY_COUNT = payNum;
        // 根据gasFee获得gasPrice和gasLimit
        Map<String, BigInteger> gasFeeMap = TransferUtils.covertZSLGasFee(gasFee);
        BigInteger gasPrice = gasFeeMap.get("gasPrice");
        BigInteger gasLimit = gasFeeMap.get("gasLimit");
        try {
            //ContractUtils.transferByUser(walletPrivateKey, toAddress, payNum);
            TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payNum, gasPrice, gasLimit);
        } catch (Exception e) {
            throw new WebException(MySystemCode.WALLET_EXCEPTION_TRANSFER_ERROR);
        }

        // 组装转账数据
        String DealNote = "云抓取任务消耗";
        //String gasFee = "0";
        WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
        walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
        walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
        walletDealDetailBO.setTransferToken(payNum);
        walletDealDetailBO.setWalletIntoAddress(toAddress);
        walletDealDetailBO.setWalletOutAddress(walletAddress);
        walletDealDetailBO.setTransType("ZSL");

        JeeTask jeeTask = new JeeTask();
        jeeTask.setParam(param);
        jeeTask.setName(System.currentTimeMillis() + "");
        jeeTask.setJobClass("com.smartdatachain.job.TaskJob");
        jeeTaskClient.submitTask(jeeTask);

        // 将转账记录存入数据库
        int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
        if (createWalletDealDetailResultInt < 0) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        return new HashMap();
    }


}

