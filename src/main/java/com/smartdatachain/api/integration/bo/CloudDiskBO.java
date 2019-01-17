package com.smartdatachain.api.integration.bo;

import java.util.Date;

/**
 * 包: com.smartdatachain.api.integration.bo
 * 源文件:CloudDiskBO.java
 * 云磁盘对象
 * @author Allen , Inc. All rights reserved.2018年10月27日
 */
public class CloudDiskBO extends BaseDiskBO
{
    private int cloudId;
    private String diskName; //磁盘名称

    public int getCloudId()
    {
        return cloudId;
    }

    public void setCloudId(int cloudId)
    {
        this.cloudId = cloudId;
    }

    public String getDiskName()
    {
        return diskName;
    }

    public void setDiskName(String diskName)
    {
        this.diskName = diskName;
    }
}
