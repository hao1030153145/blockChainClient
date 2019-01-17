package com.smartdatachain.api.integration.bo;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DappBO {

    private int id;
    @ApiModelProperty(value = "dapp名称")
    private String dappName;
    @ApiModelProperty(value = "dapp介绍")
    private String dappIntro;
    @ApiModelProperty(value = "mac地址")
    private String macAddress;
    @ApiModelProperty(value = "token")
    private String token;

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
}
