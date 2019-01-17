package com.smartdatachain.api.web.po;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DappPO {

    private int id;
    @ApiModelProperty(value = "dapp名称")
    private String dappName;
    @ApiModelProperty(value = "dapp介绍")
    private String dappIntro;
    @ApiModelProperty(value = "mac地址")
    private String macAddress;
    @ApiModelProperty(value = "dapp关联数据库表")
    private List<String> dappRelevantTable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDappName() {
        return dappName;
    }

    public void setDappName(String dappName) {
        this.dappName = dappName;
    }

    public String getDappIntro() {
        return dappIntro;
    }

    public void setDappIntro(String dappIntro) {
        this.dappIntro = dappIntro;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public List<String> getDappRelevantTable() {
        return dappRelevantTable;
    }

    public void setDappRelevantTable(List<String> dappRelevantTable) {
        this.dappRelevantTable = dappRelevantTable;
    }
}
