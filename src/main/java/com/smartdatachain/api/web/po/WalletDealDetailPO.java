package com.smartdatachain.api.web.po;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class WalletDealDetailPO {

    private int id;
    @ApiModelProperty(value = "交易时间")
    private Date dealTime;
    @ApiModelProperty(value = "钱包转出地址")
    private String walletOutAddress;
    @ApiModelProperty(value = "钱包转入地址")
    private String walletIntoAddress;
    @ApiModelProperty(value = "转账金额")
    private String transferToken;
    @ApiModelProperty(value = "钱包转账费用 == 旷工费")
    private String gasFee;
    @ApiModelProperty(value = "交易备注")
    private String dealNote;
    @ApiModelProperty(value = "转账类型")
    private String transType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getWalletOutAddress() {
        return walletOutAddress;
    }

    public void setWalletOutAddress(String walletOutAddress) {
        this.walletOutAddress = walletOutAddress;
    }

    public String getWalletIntoAddress() {
        return walletIntoAddress;
    }

    public void setWalletIntoAddress(String walletIntoAddress) {
        this.walletIntoAddress = walletIntoAddress;
    }

    public String getTransferToken() {
        return transferToken;
    }

    public void setTransferToken(String transferToken) {
        this.transferToken = transferToken;
    }

    public String getGasFee() {
        return gasFee;
    }

    public void setGasFee(String gasFee) {
        this.gasFee = gasFee;
    }

    public String getDealNote() {
        return dealNote;
    }

    public void setDealNote(String dealNote) {
        this.dealNote = dealNote;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
