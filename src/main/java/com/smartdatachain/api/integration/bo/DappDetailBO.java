package com.smartdatachain.api.integration.bo;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DappDetailBO {

    private int id;
    @ApiModelProperty(value = "dapp名称")
    private String DappName;
    @ApiModelProperty(value = "dapp介绍")
    private String DappIntro;
    @ApiModelProperty(value = "dapp关联数据库表")
    private String DappRelevantTable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDappName() {
        return DappName;
    }

    public void setDappName(String dappName) {
        DappName = dappName;
    }

    public String getDappIntro() {
        return DappIntro;
    }

    public void setDappIntro(String dappIntro) {
        DappIntro = dappIntro;
    }

    public String getDappRelevantTable() {
        return DappRelevantTable;
    }

    public void setDappRelevantTable(String dappRelevantTable) {
        DappRelevantTable = dappRelevantTable;
    }
}
