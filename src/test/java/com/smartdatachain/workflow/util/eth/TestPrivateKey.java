/*
 * @project: smartdatachaincrawlerapi
 * @package: com.smartdatachain.workflow.util.eth
 * @title:   TestPrivateKey.java
 *
 * Copyright (c) 2018 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.workflow.util.eth;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2018-10-27 12:39
 */
public class TestPrivateKey {
    @Test
    public void testGenPrivateKey() {
        try {
            String seed = UUID.randomUUID().toString();
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();

            String sPrivatekeyInHex = privateKeyInDec.toString(16);

            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
            String sAddress = aWallet.getAddress();


            System.out.println("address == " + "0x" + sAddress);
            System.out.println("privatekey == " + sPrivatekeyInHex);
            Assert.assertNotNull(sPrivatekeyInHex);
        } catch (Exception e) {

        }
    }


    @Test
    /*******连接以太坊客户端**************/
    public void testConectETHclient() throws IOException {
        //连接方式1：使用infura 提供的客户端
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        // TODO:
        // 2018/4/10 token更改为自己的
        //连接方式2：使用本地客户端
        //web3j = Web3j.build(new HttpService("127.0.0.1:7545"));
        //测试是否连接成功
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        System.out.println("version=" + web3ClientVersion);
    }


    /***********查询指定地址的余额***********/
    @Test
    public void getBlanceOf() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;
        String address = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";//等待查询余额的地址
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        //格式转化 wei-ether
        String blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString()
                .concat(" ether");
        System.out.println(blanceETH);
    }

    @Test
    /****************交易*****************/
    public void transto() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials  credentials = Credentials.create("6DFCCE26F2BDAD72295931B26670DD77725C964DBF2097BDB8C150473FFECA47");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_to = "0x0E7cEB98CE5015D6EAE8517585576Eb7177FAe6f";

        BigDecimal amountEther = BigDecimal.valueOf(0.01);
        BigInteger amountWei = Convert.toWei(amountEther, Convert.Unit.ETHER).toBigInteger();

        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, address_to, amountEther, Convert.Unit
                .ETHER).send();

        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + send.getTransactionHash());
        System.out.println("from :" + send.getFrom());
        System.out.println("to:" + send.getTo());
        System.out.println("gas used=" + send.getGasUsed());
        System.out.println("status: " + send.getStatus());
    }


}
