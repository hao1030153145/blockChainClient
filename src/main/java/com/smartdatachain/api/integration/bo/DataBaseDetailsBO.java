package com.smartdatachain.api.integration.bo;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class DataBaseDetailsBO {

    private int id;
    @ApiModelProperty(value = "数据库名字")
    private String dataBaseName;
    @ApiModelProperty(value = "钱包地址")
    private String walletId;
    @ApiModelProperty(value = "数据库密码")
    private String dataBasePassword;
    @ApiModelProperty(value = "数据库表名")
    private String dataBaseTableName;

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

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getDataBasePassword() {
        return dataBasePassword;
    }

    public void setDataBasePassword(String dataBasePassword) {
        this.dataBasePassword = dataBasePassword;
    }

    public String getDataBaseTableName() {
        return dataBaseTableName;
    }

    public void setDataBaseTableName(String dataBaseTableName) {
        this.dataBaseTableName = dataBaseTableName;
    }
}
