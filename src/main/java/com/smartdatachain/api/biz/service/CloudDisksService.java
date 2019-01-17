package com.smartdatachain.api.biz.service;

import com.jeeframework.logicframework.biz.service.BizService;
import com.smartdatachain.api.integration.bo.CloudDataBaseBO;
import com.smartdatachain.api.integration.bo.CloudDiskBO;

import java.util.List;

/**
 * 包: com.smartdatachain.api.biz.service
 * 源文件:CloudDisksService.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public interface CloudDisksService extends BizService
{

    //云磁盘
    int addCloudDisk(String diskName,int useLength,double size,String walletPublicKey);

    List<CloudDiskBO> getCloudDiskBO(String walletId);

    CloudDiskBO getCloudDisk(int cloudId );

    void dilatationDiskCap(int diskId,int addSizeNum);

    void dilatationUseFulTime(int days,int diskId);

    void updateCloudFile(int diskId,double usedCap);
    //数据库

    int addCloadDatabase(String name,int useLength,double size,String walletPublicKey);

    List<CloudDataBaseBO> getCloudDatabases(String walletId);

    void dilatationDatabaseCap(int databaseId,int addSizeNum);

    CloudDataBaseBO getCloudDatabaseBO(int databaseId);

    void dilatationDatabaseUseFulTime(int days,int databaseId);

    void updateDatabasePWD(int database,String pwd);

    void updateCloudDatabase(int database,double usedCap);

    void deleteDiskByTime();

    void deleteDBByTime();



}
