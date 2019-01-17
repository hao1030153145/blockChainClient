package com.smartdatachain.api.web.po;

import com.smartdatachain.api.integration.bo.DataBaseDetailsBO;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DataBaseDetailsPO {

    private int id;
    @ApiModelProperty(value = "dapp名称")
    private String dappName;
    @ApiModelProperty(value = "dapp简介")
    private String dappIntro;
    @ApiModelProperty(value = "mac地址")
    private String macAddress;
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "dapp下的关联数据库信息")
    private List<DataBaseDetailsBO> dataBaseDetailsBOS;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DataBaseDetailsBO> getDataBaseDetailsBOS() {
        return dataBaseDetailsBOS;
    }

    public void setDataBaseDetailsBOS(List<DataBaseDetailsBO> dataBaseDetailsBOS) {
        this.dataBaseDetailsBOS = dataBaseDetailsBOS;
    }
}
