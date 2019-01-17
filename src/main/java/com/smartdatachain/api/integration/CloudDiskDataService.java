package com.smartdatachain.api.integration;

import com.jeeframework.logicframework.integration.DataService;
import com.smartdatachain.api.integration.bo.CloudDataBaseBO;
import com.smartdatachain.api.integration.bo.CloudDiskBO;

import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.integration
 * 源文件:CloudDiskDataService.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public interface CloudDiskDataService extends DataService
{
    int addCloudDisk(CloudDiskBO cloudDiskBO);

    List<CloudDiskBO> getCloudDisks(Map<String,Object> params);

    void updateCloudDisk(Map<String,Object> params);

    int addCloadDatabase(CloudDataBaseBO dataBaseBO);

    List<CloudDataBaseBO> getCloudDatabases(Map<String,Object> param);

    void updateCloudDatabase(Map<String,Object> params);

    void deleteDiskByTime();

    void deleteDBByTime();
}
