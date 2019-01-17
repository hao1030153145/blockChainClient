/*
 * @project: smartdatachaincrawlerapi
 * @package: com.smartdatachain.api.util.eth.contract
 * @title:   ContractUtils.java
 *
 * Copyright (c) 2018 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.api.util.eth.contract;

import com.smartdatachain.api.web.wallet.DefaultGasProviderWallet;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 转账工具类2.0
 *
 * @author haolen
 * @version 1.0 2018-11-08 14:52
 */
public class TransferUtils extends Transfer {

    public static String INFURA_URL_TEST = "https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"; // 测试网络
    public static String INFURA_URL_MAIN = "https://mainnet.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"; // 主干网络

    public static String OWNER_PRIVATE_KEY = "61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626";
    public static String CONTRACT_ADDRESS = "0x59C044A75249eC5e0bEF5b1F0C8493E643674d46";                   // ZSL的token地址


    public TransferUtils(Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
    }

    // 获得当前节点的gasPrices
    public static BigInteger getCurrentGasFee() throws Exception {
        Credentials credentials = Credentials.create
                (OWNER_PRIVATE_KEY);
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL_TEST));
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
        ContractUtils contractUtils = new ContractUtils(web3j, transactionManager);
        BigInteger gasPrice = contractUtils.requestCurrentGasPrice();
        return gasPrice;
    }

    // ETH转账
    public static void transferEthFroGasFee(String privateKey, String toAddress, String moneyAmount, BigInteger gasPrice, BigInteger gasLimit) throws Exception {
        Credentials credentials = Credentials.create
                (privateKey);
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL_TEST));
        TransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
        Transfer transfer = new Transfer(web3j, transactionManager);

        BigDecimal amountEther = new BigDecimal(moneyAmount);
        transfer.sendFunds(toAddress, amountEther, Convert.Unit.ETHER, gasPrice, gasLimit).send();
    }

    // ZSL转账
    public static void transferZSLFroGasFee(String privateKey, String toAddress, String moneyAmount, BigInteger gasPrice, BigInteger gasLimit) throws Exception {

        Web3j web3j = Web3j.build(new HttpService(INFURA_URL_TEST));
        if (web3j == null) throw new RuntimeException("初始化web3j 客户端出错");

        Credentials credentials = Credentials.create(privateKey);
        if (credentials == null) throw new RuntimeException("初始化用户私钥出错");

        TokenERC20 tokenERC20 = TokenERC20.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProviderWallet(gasPrice, gasLimit));

        RemoteCall<TransactionReceipt> transfer = tokenERC20.transfer(toAddress, new BigInteger(transformNumberCovert(moneyAmount)));

        TransactionReceipt send = transfer.send();

    }

    // 如果是转账Eth的手续费，转换如下
    public static Map<String, BigInteger> covertETHGasFee(String gasFee) {

        Map<String, BigInteger> gasFeeMap = new HashMap<>();
        BigDecimal bigDecimal = new BigDecimal(gasFee);
        BigDecimal bigDecimal1 = new BigDecimal("10").pow(18);
        BigDecimal bigDecimal2 = bigDecimal.multiply(bigDecimal1);
        BigDecimal gasLimitDefault = new BigDecimal("21000");

        BigDecimal gasPriceDefault = bigDecimal2.divide(gasLimitDefault, 0);
        BigInteger gasLimit = gasLimitDefault.toBigInteger();
        BigInteger gasPrice = gasPriceDefault.toBigInteger();

        gasFeeMap.put("gasPrice", gasPrice);
        gasFeeMap.put("gasLimit", gasLimit);
        return gasFeeMap;
    }

    // 如果是智能合约的手续费，转换如下
    public static Map<String, BigInteger> covertZSLGasFee(String gasFee) {

        Map<String, BigInteger> gasFeeMap = new HashMap<>();
        BigDecimal bigDecimal = new BigDecimal(gasFee);
        BigDecimal bigDecimal1 = new BigDecimal("10").pow(18);
        BigDecimal bigDecimal2 = bigDecimal.multiply(bigDecimal1);
        BigInteger gasPrice = new BigInteger("1000000000");
        BigInteger gasLimit = bigDecimal2.toBigInteger().divide(gasPrice);

        gasFeeMap.put("gasPrice", gasPrice);
        gasFeeMap.put("gasLimit", gasLimit);
        return gasFeeMap;
    }

    private static String transformNumberCovert(String moneyAmount) {
        String[] moneyAmountArray = moneyAmount.split("\\.");
        String numberTmp = null;
        String numberDot = null;
        if (moneyAmountArray.length == 0) {
            numberTmp = moneyAmount;
        } else {
            numberTmp = moneyAmountArray[0];
        }
        if (moneyAmountArray.length > 1) {
            numberDot = moneyAmountArray[1];
        }
        System.out.println("numberTmp的值为："+numberTmp);

        if (numberDot != null) {
            int leftZero = 18 - numberDot.length();
            for (int i = 0; i < leftZero; i++) {
                numberDot = numberDot + "0";
            }
            if (Long.valueOf(numberTmp) > 0) {
                numberTmp = numberTmp + numberDot;
            } else {
                numberTmp = numberDot;
            }
            System.out.println("numberTmp的值为："+numberTmp);
        } else {
            numberTmp = numberTmp + "000000000000000000";
        }
        return numberTmp;
    }
}
