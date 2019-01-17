/*
 * @project: smartdatachaincrawlerapi
 * @package: com.smartdatachain.workflow.util.eth
 * @title:   TestPrivateKey.java
 *
 * Copyright (c) 2018 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.workflow.util.eth;

import com.smartdatachain.api.util.eth.contract.TokenERC20;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.JsonRpc2_0Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import rx.Observable;
import rx.Subscription;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2018-10-27 12:39
 */
public class TestContract {


    @Test
    /****************奖励积分*****************/
    public void reward() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_madao = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";

        BigDecimal amountEther = BigDecimal.valueOf(0.123);

        // 加载合约
        Function function = new Function(
                "transfer",
                Arrays.<Type>asList(new Address(address_madao),
                        new Uint256(new BigInteger("12800000000000000000000000"))),
                Collections.<TypeReference<?>>emptyList());
        String encodedFunction = FunctionEncoder.encode(function);

// 创建 tx 管理器，并通过 txManager 来发起合约转账
        RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);
        EthSendTransaction transactionResponse = txManager.sendTransaction(
                DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT,
                "0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", encodedFunction, BigInteger.ZERO);

// 获取 TxHash
        String transactionHash = transactionResponse.getTransactionHash();


        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + transactionResponse.getTransactionHash());
        System.out.println("resultOf  =  " + transactionResponse.getResult());
        System.out.println("rawResponse  =  " + transactionResponse.getRawResponse());
    }

    @Test
    /****************查看余额*****************/
    public void balanceOf() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_madao = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());

        RemoteCall<BigInteger> balance = tokenERC20.balanceOf(address_madao);


        System.out.println("Transaction complete:");
        System.out.println("rawResponse  =  " + balance.send());
    }


    @Test
    /****************转账 这个有大小限制，只能转1.28*****************/
    public void transfer() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_madao = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());

        RemoteCall<TransactionReceipt> transfer = tokenERC20.transfer(address_madao, BigInteger.valueOf
                (1280000000000000000L));


        TransactionReceipt send = transfer.send();

        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + send.getTransactionHash());
        System.out.println("from :" + send.getFrom());
        System.out.println("to:" + send.getTo());
        System.out.println("gas used=" + send.getGasUsed());
        System.out.println("status: " + send.getStatus());


    }

    @Test
    /****************授权可以委托转账
     *
     * 这个需要lanceAccount1 授权才能转账
     *
     * *****************/
    public void approve() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String lanceAccount2 = "0xcCCF7AB82D7e401658E8A92fc88B552E4EE71DFA";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());


        RemoteCall<TransactionReceipt> balance = tokenERC20.approve(lanceAccount2, BigInteger.valueOf
                (8880000000000000000L));


        TransactionReceipt send = balance.send();

        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + send.getTransactionHash());
        System.out.println("from :" + send.getFrom());
        System.out.println("to:" + send.getTo());
        System.out.println("gas used=" + send.getGasUsed());
        System.out.println("status: " + send.getStatus());
    }

    @Test
    /****************授权可以委托转账,查询余额
     *
     * 这个需要lanceAccount1 授权才能转账
     *
     * *****************/
    public void allowance() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String lanceAccount2 = "0xcCCF7AB82D7e401658E8A92fc88B552E4EE71DFA";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());
        String lanceAccount1 = "0xcdDe0f7E0B2C8E74a6049B4B94E12103c51BA7ec";


        RemoteCall<BigInteger> allowance = tokenERC20.allowance(lanceAccount1, lanceAccount2);


        BigInteger count = allowance.send();

        System.out.println("Transaction complete:");
        System.out.println("trans count =" + count);

    }

    @Test
    /****************测试指定委托账户转账 这个有大小限制，只能转1.28
     *
     * 这个需要lanceAccount1 授权才能转账
     *
     * *****************/
    public void transferFrom() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        //lanceAccount2 的私钥
        Credentials credentials = Credentials.create
                ("0ac7e7c6a4b50be1d0e91832e0c50db860db866c04f59b7465078871eff63bad");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_madao = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());
        String lanceAccount2 = "0xcCCF7AB82D7e401658E8A92fc88B552E4EE71DFA";

        String lanceAccount1 = "0xcdDe0f7E0B2C8E74a6049B4B94E12103c51BA7ec";
        RemoteCall<TransactionReceipt> transfer = tokenERC20.transferFrom(
                lanceAccount1, lanceAccount2, BigInteger.valueOf(1));


        TransactionReceipt send = transfer.send();

        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + send.getTransactionHash());
        System.out.println("from :" + send.getFrom());
        System.out.println("to:" + send.getTo());
        System.out.println("gas used=" + send.getGasUsed());
        System.out.println("status: " + send.getStatus());


    }


    @Test
    /****************转账 用户登录自己转账 1.28*****************/
    public void transferByLance() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("0ac7e7c6a4b50be1d0e91832e0c50db860db866c04f59b7465078871eff63bad");
        if (credentials == null) return;
        //开始发送0.01 =eth到指定地址
        String address_madao = "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb";

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());

        RemoteCall<TransactionReceipt> transfer = tokenERC20.transfer(address_madao, BigInteger.valueOf
                (1280000000000000000L));


        TransactionReceipt send = transfer.send();

        System.out.println("Transaction complete:");
        System.out.println("trans hash=" + send.getTransactionHash());
        System.out.println("from :" + send.getFrom());
        System.out.println("to:" + send.getTo());
        System.out.println("gas used=" + send.getGasUsed());
        System.out.println("status: " + send.getStatus());


    }

    //https://api-rinkeby.etherscan.io/api?module=logs&action=getLogs&fromBlock=0&toBlock=latest&address
    // =0x59C044A75249eC5e0bEF5b1F0C8493E643674d46&topic0
    // =0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef&topic1
    // =0x000000000000000000000000cdDe0f7E0B2C8E74a6049B4B94E12103c51BA7ec

    //    https://api.etherscan.io/api?module=logs&action=getLogs&fromBlock=0&toBlock=latest&address
    // =0x59C044A75249eC5e0bEF5b1F0C8493E643674d46&topic0
    // =0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef
//    https://api.etherscan.io/api?module=logs&action=getLogs&fromBlock=0&toBlock=latest&address=[Token Contract
// Address]&topic0=0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef
//            &topic1=[From Address, padded to 32 bytes - optional]
//            &topic2=[To Address, padded to 32 bytes - optional]
//    https://api.etherscan.io/api?module=logs&action=getLogs&fromBlock=379224&toBlock=latest&address
// =0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0&topic0=0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef
    @Test
    /****************  查询eth的交易记录 infura 不支持*****************/
    public void queryTransactionHistory() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;


        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());

        Observable<TokenERC20.TransferEventResponse> t = tokenERC20.transferEventObservable(DefaultBlockParameterName
                .EARLIEST, DefaultBlockParameterName.LATEST);
        t.subscribe(tx -> {
//            EthBlock.Block ethBlock = null;
//            try {
//                ethBlock = web3j.ethGetBlockByHash(tx.log.getBlockHash(), false).send().getBlock();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String timestamp = ethBlock.getTimestamp().toString(); //获取交易时间


            System.out.println(tx.from);
        });

//        .subscribe(tx -> {
//            String toAddress = tx.to;
//            String fromAddress = tx.from;
//            String txHash = tx.log.toString();
//        });


//        List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send
//                ().getBlock().getTransactions();
//        txs.forEach(tx -> {
//            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//
//            System.out.println(transaction.getFrom());
//            System.out.println(transaction.getCreates());
//            System.out.println(transaction.getGas());
//            System.out.println(transaction.getGasPrice());
//            System.out.println(transaction.getTo());
//            System.out.println("transaction.getValueRaw()   ==  " + transaction.getValueRaw());
//            System.out.println(transaction.getRaw());
//            System.out.println(transaction.getR());
//            System.out.println("transaction.getTransactionIndexRaw()  == " + transaction.getTransactionIndexRaw());
//            System.out.println("transaction.getBlockHash()  == " + transaction.getBlockHash());
//    });


        System.out.println("Transaction complete:");


    }

    //http://api.etherscan.io/api?module=account&action=tokentx&address=0x4e83362442b8d1bec281594cea3050c8eb01311c
    // &startblock=0&endblock=999999999&sort=asc&apikey=YourApiKeyToken

    @Test
    /****************  查询eth的交易详细信息*****************/
    public void queryTransactionDetail() throws Exception {
        Web3jWrapper web3j = new Web3jWrapper(new HttpService("https://rinkeby.infura" +
                ".io/v3/3cd01b51b56846b1b9993a258e508e91"));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;


        EthTransaction ethBlock = null;
        List<String> ss = new ArrayList<>();
        ss.add("0x5bc510a40e36ba1fba54fab6a82701a8797ee985ef0342b2f7f10c7043fca90c");
//        ss.add("0x4786ef035f6febdbf3072228d208c6c9389abd26b2271731c72aa16bd7aed6eb");

        try {
            ethBlock = web3j.ethGetTransactionByHashList
                    (ss).send();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Optional<Transaction> transaction = ethBlock.getTransaction();
        System.out.println(transaction.get().getFrom());
        System.out.println(transaction.get().getCreates());
        System.out.println(transaction.get().getGas());
        System.out.println(transaction.get().getGasPrice());
        System.out.println(transaction.get().getTo());
        System.out.println("transaction.getValue()   ==  " + transaction.get().getValue());
        System.out.println(transaction.get().getRaw());
        System.out.println(transaction.get().getR());
        System.out.println("transaction.getTransactionIndexRaw()  == " + transaction.get().getTransactionIndexRaw());
        System.out.println("transaction.getBlockHash()  == " + transaction.get().getBlockHash());
        String inputData = transaction.get().getInput();
        String method = inputData.substring(0, 10);
        System.out.println(method);
        String to = inputData.substring(10, 74);
        String value = inputData.substring(74);
        Method refMethod = TypeDecoder.class.getDeclaredMethod("decode", String.class, int.class, Class.class);
        refMethod.setAccessible(true);
        Address address = (Address) refMethod.invoke(null, to, 0, Address.class);
        System.out.println(address.toString());
        Uint256 amount = (Uint256) refMethod.invoke(null, value, 0, Uint256.class);
        System.out.println(amount.getValue());


//        .subscribe(tx -> {
//            String toAddress = tx.to;
//            String fromAddress = tx.from;
//            String txHash = tx.log.toString();
//        });


//        List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send
//                ().getBlock().getTransactions();
//        txs.forEach(tx -> {
//            EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) tx.get();
//
//            System.out.println(transaction.getFrom());
//            System.out.println(transaction.getCreates());
//            System.out.println(transaction.getGas());
//            System.out.println(transaction.getGasPrice());
//            System.out.println(transaction.getTo());
//            System.out.println("transaction.getValueRaw()   ==  " + transaction.getValueRaw());
//            System.out.println(transaction.getRaw());
//            System.out.println(transaction.getR());
//            System.out.println("transaction.getTransactionIndexRaw()  == " + transaction.getTransactionIndexRaw());
//            System.out.println("transaction.getBlockHash()  == " + transaction.getBlockHash());
//    });


        System.out.println("Transaction complete:");


    }

    @Test
    /****************  查询eth的交易详细信息 websocket*****************/
    public void queryTransactionDetailWebsocket() throws Exception {
        Web3j web3j = Web3j.build(new WebSocketService("wss://rinkeby.infura.io/ws", true));
        if (web3j == null) return;

        Credentials credentials = Credentials.create
                ("61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626");
        if (credentials == null) return;

        TokenERC20 tokenERC20 = TokenERC20.load("0x59C044A75249eC5e0bEF5b1F0C8493E643674d46", web3j, credentials, new
                DefaultGasProvider());
        Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
            System.out.println(tx.getBlockHash());
        });

//        Observable<TokenERC20.TransferEventResponse> t = tokenERC20.transferEventObservable(DefaultBlockParameterName
//                .EARLIEST, DefaultBlockParameterName.LATEST);
//        t.subscribe(tx -> {
//            System.out.println(tx.from);
//        });

        System.out.println("Transaction complete:");


    }


    public class Web3jWrapper extends JsonRpc2_0Web3j {


        public Web3jWrapper(Web3jService web3jService) {
            super(web3jService);
        }

        public Web3jWrapper(Web3jService web3jService, long pollingInterval, ScheduledExecutorService
                scheduledExecutorService) {
            super(web3jService, pollingInterval, scheduledExecutorService);
        }


        public Request<?, EthTransaction> ethGetTransactionByHashList(List<String> transactionHashList) {
            return new Request<>(
                    "eth_getTransactionByHash",
                    transactionHashList,
                    web3jService,
                    EthTransaction.class);
        }
    }
}
