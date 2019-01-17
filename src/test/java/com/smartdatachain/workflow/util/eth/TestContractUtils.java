/*
 * @project: smartdatachaincrawlerapi
 * @package: com.smartdatachain.workflow.util.eth
 * @title:   TestPrivateKey.java
 *
 * Copyright (c) 2018 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.workflow.util.eth;

import com.smartdatachain.api.util.eth.contract.ContractUtils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.junit.Test;
import org.web3j.crypto.Credentials;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2018-10-27 12:39
 */
public class TestContractUtils {


    @Test
    /****************奖励积分*****************/
    public void reward() throws Exception {

        ContractUtils.reward("0xcCCF7AB82D7e401658E8A92fc88B552E4EE71DFA", "0.123");

    }

    @Test
    /****************查看余额*****************/
    public void balanceOf() throws Exception {
        String balance = ContractUtils.balanceOf("0xcCCF7AB82D7e401658E8A92fc88B552E4EE71DFA");


        System.out.println("Transaction complete:");
        System.out.println("rawResponse  =  " + balance);
    }


    @Test
    /****************转账 这个有大小限制，只能转1.28*****************/
    public void transfer() throws Exception {
        ContractUtils.transferByUser("0ac7e7c6a4b50be1d0e91832e0c50db860db866c04f59b7465078871eff63bad",
                "0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb", "11");

    }

    @Test
    public  void genMnemonicCode()throws UnreadableWalletException, IOException {

        String passphrase = "";
        SecureRandom secureRandom = new SecureRandom();
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed deterministicSeed = new DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
        List<String> mnemonicCode = deterministicSeed.getMnemonicCode();
        System.out.println(String.join(" ", mnemonicCode));

    }

    @Test
    public  void genPrivateKeyByMnemonicCode()throws UnreadableWalletException, IOException {
        String seedCode = "client dune unfair assume level width bind control mad member old crystal";

        // BitcoinJ
        DeterministicSeed seed = new DeterministicSeed(seedCode, null, "", 1409478661L);
        DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
        List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
        DeterministicKey key = chain.getKeyByPath(keyPath, true);
        BigInteger privKey = key.getPrivKey();

        // Web3j

        Credentials credentials = Credentials.create(privKey.toString(16));
        String address = credentials.getAddress();
        String privateKey = privKey.toString(16);
        System.out.println(address);
        System.out.println(privateKey);
    }

}
