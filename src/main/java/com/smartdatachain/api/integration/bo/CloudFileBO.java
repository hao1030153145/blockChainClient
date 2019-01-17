package com.smartdatachain.api.integration.bo;

import java.util.Date;

/**
 * 包: com.smartdatachain.api.integration.bo
 * 源文件:CloudFileBO.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public class CloudFileBO
{
    private int id;
    private String path;
    private String name;
    private int cloudId;
    private String walletId;
    private Long fileSize;
    private Date createTime;

    public Long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCloudId()
    {
        return cloudId;
    }

    public void setCloudId(int cloudId)
    {
        this.cloudId = cloudId;
    }

    public String getWalletId()
    {
        return walletId;
    }

    public void setWalletId(String walletId)
    {
        this.walletId = walletId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
