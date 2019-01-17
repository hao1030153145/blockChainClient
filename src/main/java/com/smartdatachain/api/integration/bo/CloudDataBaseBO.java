package com.smartdatachain.api.integration.bo;

import org.codehaus.jackson.map.Serializers;

/**
 * 包: com.smartdatachain.api.integration.bo
 * 源文件:CloudDataBaseBO.java
 * 数据库
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public class CloudDataBaseBO extends BaseDiskBO
{
    private int dataBaseId;
    private String name; //磁盘名称
    private String databasePwd;

    public int getDataBaseID()
    {
        return dataBaseId;
    }

    public void setDataBaseID(int dataBaseId)
    {
        this.dataBaseId = dataBaseId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
       this.name = name;
    }

    public String getDatabasePwd()
    {
        return databasePwd;
    }

    public void setDatabasePwd(String databasePwd)
    {
        this.databasePwd = databasePwd;
    }
}
