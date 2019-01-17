package com.smartdatachain.api.biz.service.impl.local;

import com.jeeframework.logicframework.biz.service.BaseService;
import com.smartdatachain.api.biz.service.CloudDisksService;
import com.smartdatachain.api.integration.CloudDiskDataService;
import com.smartdatachain.api.integration.bo.CloudDataBaseBO;
import com.smartdatachain.api.integration.bo.CloudDiskBO;
import com.smartdatachain.api.util.FileUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.biz.service.impl.local
 * 源文件:CloudDiskServicePojo.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
@Service("cloudDisksService")
public class CloudDiskServicePojo extends BaseService implements
        CloudDisksService
{
    @Resource
    private CloudDiskDataService cloudDiskDataService;


    @Override
    public int addCloudDisk(String diskName, int useLength, double size,String walletPublicKey)
    {
        CloudDiskBO cloudDiskBO =new CloudDiskBO();
        cloudDiskBO.setDiskName(diskName);
        cloudDiskBO.setCapacity(size);
        Date nowDate = new Date();
        cloudDiskBO.setStartTime(nowDate);
        cloudDiskBO.setUsefulLength(useLength);
        cloudDiskBO.setWalletID(walletPublicKey);
        Date endTime = DateUtils.addDays(nowDate,useLength);
        cloudDiskBO.setEndTime(endTime);
        cloudDiskDataService.addCloudDisk(cloudDiskBO);
        return 0;
    }

    @Override
    public List<CloudDiskBO> getCloudDiskBO(String walletId)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("walletId",walletId);
        return cloudDiskDataService.getCloudDisks(param);
    }

    @Override
    public CloudDiskBO getCloudDisk(int cloudId)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("cloudId",cloudId);
        List<CloudDiskBO> cloudDiskBOS= cloudDiskDataService.getCloudDisks(param);
        if (cloudDiskBOS.size()>0){
            return cloudDiskBOS.get(0);
        }
        return null;
    }

    @Override
    public void dilatationDiskCap(int diskId, int addSizeNum)
    {
        CloudDiskBO cloudDiskBO = getCloudDisk(diskId);
        if (cloudDiskBO !=null){
            double size = cloudDiskBO.getCapacity();
            double newSize = size+(addSizeNum*1024);
            Map<String,Object> param = new HashMap<>();
            param.put("id",cloudDiskBO.getCloudId());
            param.put("capacity",newSize);
            cloudDiskDataService.updateCloudDisk(param);
        }
    }

    @Override
    public void updateCloudFile(int diskId, double usedCap)
    {
        CloudDiskBO cloudDiskBO = getCloudDisk(diskId);
        if (cloudDiskBO !=null){
            Map<String,Object> param = new HashMap<>();
            param.put("id",cloudDiskBO.getCloudId());
            param.put("usedCap",usedCap);
            cloudDiskDataService.updateCloudDisk(param);
        }
    }

    @Override
    public int addCloadDatabase(String name, int useLength, double size,
            String walletPublicKey)
    {
        CloudDataBaseBO cloudDataBaseBO = new CloudDataBaseBO();
        cloudDataBaseBO.setName(name);
        cloudDataBaseBO.setCapacity(size);
        Date nowDate = new Date();
        cloudDataBaseBO.setStartTime(nowDate);
        cloudDataBaseBO.setUsefulLength(useLength);
        cloudDataBaseBO.setWalletID(walletPublicKey);
        cloudDataBaseBO.setDatabasePwd(FileUtil.getRandomPWd(6));
        Date endTime = DateUtils.addDays(nowDate,useLength);
        cloudDataBaseBO.setEndTime(endTime);
        return cloudDiskDataService.addCloadDatabase(cloudDataBaseBO);
    }

    @Override
    public List<CloudDataBaseBO> getCloudDatabases(String walletId)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("walletId",walletId);
        return cloudDiskDataService.getCloudDatabases(param);
    }

    @Override
    public void dilatationDatabaseCap(int databaseId, int addSizeNum)
    {
        CloudDataBaseBO cloudDataBaseBO= getCloudDatabaseBO(databaseId);
        if (cloudDataBaseBO != null){
            double size = cloudDataBaseBO.getCapacity();
            double newSize = size+(addSizeNum*1024);
            Map<String,Object> param = new HashMap<>();
            param.put("id",cloudDataBaseBO.getDataBaseID());
            param.put("capacity",newSize);
            cloudDiskDataService.updateCloudDatabase(param);
        }
    }

    @Override
    public CloudDataBaseBO getCloudDatabaseBO(int databaseId)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("databaseId",databaseId);
        List<CloudDataBaseBO> cloudDataBaseBOS= cloudDiskDataService.getCloudDatabases(param);
        if (cloudDataBaseBOS.size()>0){
            return cloudDataBaseBOS.get(0);
        }
        return null;
    }

    @Override
    public void dilatationUseFulTime(int days, int diskId)
    {
        CloudDiskBO cloudDiskBO = getCloudDisk(diskId);
        if (cloudDiskBO !=null){
            int size = cloudDiskBO.getUsefulLength();
            int newSize = size+days;
            Date finalDate = DateUtils.addDays(cloudDiskBO.getEndTime(),days);
            Map<String,Object> param = new HashMap<>();
            param.put("id",cloudDiskBO.getCloudId());
            param.put("usefulLength",newSize);
            param.put("endTime",finalDate);
            cloudDiskDataService.updateCloudDisk(param);
        }
    }

    @Override
    public void dilatationDatabaseUseFulTime(int days, int databaseId)
    {
        CloudDataBaseBO cloudDataBaseBO= getCloudDatabaseBO(databaseId);
        if (cloudDataBaseBO != null){
            double size = cloudDataBaseBO.getCapacity();
            double newSize = size+cloudDataBaseBO.getUsefulLength();
            Date finalDate = DateUtils.addDays(cloudDataBaseBO.getEndTime(),days);
            Map<String,Object> param = new HashMap<>();
            param.put("id",cloudDataBaseBO.getDataBaseID());
            param.put("usefulLength",newSize);
            param.put("endTime",finalDate);
            cloudDiskDataService.updateCloudDatabase(param);
        }
    }

    @Override
    public void updateDatabasePWD(int database, String pwd)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("id",database);
        param.put("databasePwd",pwd);
        cloudDiskDataService.updateCloudDatabase(param);
    }

    @Override
    public void updateCloudDatabase(int database, double usedCap)
    {

            Map<String,Object> param = new HashMap<>();
            param.put("id",database);
            param.put("usedCap",usedCap);
            cloudDiskDataService.updateCloudDatabase(param);

    }

    @Override
    public void deleteDiskByTime() {
        cloudDiskDataService.deleteDiskByTime();
    }

    @Override
    public void deleteDBByTime() {
        cloudDiskDataService.deleteDBByTime();
    }
}
