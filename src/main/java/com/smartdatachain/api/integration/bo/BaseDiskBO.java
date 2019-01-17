package com.smartdatachain.api.integration.bo;

import java.util.Date;

/**
 * 包: com.smartdatachain.api.integration.bo
 * 源文件:BaseDiskBO.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public class  BaseDiskBO
{
    private String walletID;
    private double  capacity;// 磁盘大小
    private Date startTime;//磁盘开始时间
    private Date endTime; //磁盘结束时间
    private int usefulLength ; //使用日长
    private double usedCap =0;//使用率

    public String getWalletID()
    {
        return walletID;
    }

    public void setWalletID(String walletID)
    {
        this.walletID = walletID;
    }

    public double getCapacity()
    {
        return capacity;
    }

    public void setCapacity(double capacity)
    {
        this.capacity = capacity;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public int getUsefulLength()
    {
        return usefulLength;
    }

    public void setUsefulLength(int usefulLength)
    {
        this.usefulLength = usefulLength;
    }

    public double getUsedCap()
    {
        return usedCap;
    }

    public void setUsedCap(double usedCap)
    {
        this.usedCap = usedCap;
    }
}
