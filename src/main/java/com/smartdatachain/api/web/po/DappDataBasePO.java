package com.smartdatachain.api.web.po;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DappDataBasePO {

    private int id;
    @ApiModelProperty(value = "数据库名字")
    private String dataBaseName;
    @ApiModelProperty(value = "数据库表名")
    private String dataBaseTableName;
    @ApiModelProperty(value = "数据库关联id")
    private String relevantDappId;
    @ApiModelProperty(value = "数据库密码")
    private String dataBaseToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseTableName() {
        return dataBaseTableName;
    }

    public void setDataBaseTableName(String dataBaseTableName) {
        this.dataBaseTableName = dataBaseTableName;
    }

    public String getRelevantDappId() {
        return relevantDappId;
    }

    public void setRelevantDappId(String relevantDappId) {
        this.relevantDappId = relevantDappId;
    }

    public String getDataBaseToken() {
        return dataBaseToken;
    }

    public void setDataBaseToken(String dataBaseToken) {
        this.dataBaseToken = dataBaseToken;
    }
}
