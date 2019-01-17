/*
 * @project: smartdatachaincrawlerapi
 * @package: com.smartdatachain.api.util.eth.contract
 * @title:   ContractUtils.java
 *
 * Copyright (c) 2018 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.api.util.eth.contract;

import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.web.exception.MySystemCode;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * 合约工具类
 *
 * @author lance
 * @version 1.0 2018-10-30 18:52
 */
public class ContractUtils extends Transfer {

    public static String INFURA_URL = "https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91";

    public static String CONTRACT_ADDRESS = "0x59C044A75249eC5e0bEF5b1F0C8493E643674d46";
    public static String OWNER_PRIVATE_KEY = "61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626";

    public static String PAY_COUNT = null;
    public static String GAS_FEE = null;

    public ContractUtils(Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
    }

    /**
     * 积分奖励
     *
     * @throws Exception
     */
    public static void reward(String toAddress, String moneyAmount) throws Exception {
        if (!Validate.isDouble(moneyAmount)) {
            throw new RuntimeException("请输入数字金额");
        }
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL));
        if (web3j == null) throw new RuntimeException("初始化web3j 客户端出错");

        Credentials credentials = Credentials.create
                (OWNER_PRIVATE_KEY);
        if (credentials == null) throw new RuntimeException("初始化管理员私钥出错");
        //开始发送0.01 =eth到指定地址
        String address_madao = toAddress;

        String numberTmp = transformNumber(moneyAmount);

//        DecimalFormat df1 = new DecimalFormat("0.000000000000000000");
//        String moneyAmountString = df1.format(moneyAmount);

//        BigDecimal amountEther = BigDecimal.valueOf(0.123);

        // 加载合约
        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new Address(address_madao),
                        new Uint256(new BigInteger(numberTmp))),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);

// 创建 tx 管理器，并通过 txManager 来发起合约转账
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
        EthSendTransaction transactionResponse = txManager.sendTransaction(
                DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT,
                CONTRACT_ADDRESS, encodedFunction, BigInteger.ZERO);

// 获取 TxHash
        String transactionHash = transactionResponse.getTransactionHash();

//        System.out.println("Transaction complete:");
//        System.out.println("trans hash=" + transactionResponse.getTransactionHash());
//        System.out.println("resultOf  =  " + transactionResponse.getResult());
//        System.out.println("rawResponse  =  " + transactionResponse.getRawResponse());
    }

    private static String transformNumber(String moneyAmount) {
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
        } else {
            numberTmp = numberTmp + "000000000000000000";
        }
        return numberTmp;
    }

    /****************ZSL转账*****************/
    public static void transferByUser(String userPrivateKey, String toAddress, String moneyAmount) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL));
        if (web3j == null) throw new RuntimeException("初始化web3j 客户端出错");

        Credentials credentials = Credentials.create
                (userPrivateKey);
        if (credentials == null) throw new RuntimeException("初始化用户私钥出错");
        //开始发送0.01 =eth到指定地址

        TokenERC20 tokenERC20 = TokenERC20.load(CONTRACT_ADDRESS, web3j, credentials, new
                DefaultGasProvider());


        RemoteCall<TransactionReceipt> transfer = tokenERC20.transfer(toAddress, new BigInteger(transformNumber
                (moneyAmount)));

        TransactionReceipt send = transfer.send();

    }

    /****************ETH转账*****************/
    public static void transferETHByUser(String privateKey, String toAddress, String moneyAmount) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL));
        if (web3j == null) throw new RuntimeException("初始化web3j 客户端出错");
        Credentials credentials = Credentials.create
                (privateKey);
        if (credentials == null) throw new RuntimeException("初始化用户私钥出错");


        BigDecimal amountEther = new BigDecimal(moneyAmount);

        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, toAddress, amountEther, Convert.Unit
                .ETHER).send();

    }


    @Test
    /***************查看ZSL余额*****************/
    public static String balanceOf(String userAddress) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(INFURA_URL));
        if (web3j == null) throw new RuntimeException("初始化web3j 客户端出错");
        Credentials credentials = Credentials.create
                (OWNER_PRIVATE_KEY);
        if (credentials == null) throw new RuntimeException("初始化管理员私钥出错");
        //开始发送0.01 =eth到指定地址
        TokenERC20 tokenERC20 = TokenERC20.load(CONTRACT_ADDRESS, web3j, credentials, new
                DefaultGasProvider());
        RemoteCall<BigInteger> balance = tokenERC20.balanceOf(userAddress);
        BigInteger balanceAmount = balance.send();
        String balanceAmountString = new BigDecimal(balanceAmount).divide(new BigDecimal("1000000000000000000")).toString();
        return balanceAmountString;
    }

    /****************查看ETH余额*****************/
    public static String balanceOfETH(String userAddress) throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) {
            throw new WebException(MySystemCode.WALLET_EXCEPTION_GETBALANCE_ERROR);
        }
        String address = userAddress;//等待查询余额的地址
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        //格式转化 wei-ether
        String blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString();
        //.concat(" ether");

        return blanceETH;
    }

    @Override
    public BigInteger requestCurrentGasPrice() throws IOException {
        return super.requestCurrentGasPrice();
    }
}
