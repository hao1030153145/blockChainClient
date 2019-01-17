package com.smartdatachain.api.web.wallet;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class DefaultGasProviderWallet implements ContractGasProvider {

    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public DefaultGasProviderWallet(BigInteger gasPrice, BigInteger gasLimit) {
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return gasPrice;
    }

    @Override
    public BigInteger getGasPrice() {
        return gasPrice;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
        return gasLimit;
    }

    @Override
    public BigInteger getGasLimit() {
        return gasLimit;
    }
}
