package com.smartdatachain.api.integration.bo;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DataBaseDetailBO {

    private int id;
    @ApiModelProperty(value = "dapp名称")
    private String dappName;
    @ApiModelProperty(value = "dapp简介")
    private String dappIntro;
    @ApiModelProperty(value = "mac地址")
    private String macAddress;
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "数据库id")
    private String dataBaseId;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDataBaseId() {
        return dataBaseId;
    }

    public void setDataBaseId(String dataBaseId) {
        this.dataBaseId = dataBaseId;
    }
}
