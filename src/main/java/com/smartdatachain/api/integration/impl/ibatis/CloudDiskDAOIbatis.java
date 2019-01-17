package com.smartdatachain.api.integration.impl.ibatis;

import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.smartdatachain.api.integration.CloudDiskDataService;
import com.smartdatachain.api.integration.bo.CloudDataBaseBO;
import com.smartdatachain.api.integration.bo.CloudDiskBO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.integration.impl.ibatis
 * 源文件:CloudDiskDAOIbatis.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
@Scope("prototype")
@Repository("cloudDiskDataService")
public class CloudDiskDAOIbatis extends BaseDaoiBATIS implements
        CloudDiskDataService
{
    @Override
    public int addCloudDisk(CloudDiskBO cloudDiskBO)
    {
        return sqlSessionTemplate.insert("cloudDiskMapper.addCloudDisk",cloudDiskBO);
    }

    @Override
    public List<CloudDiskBO> getCloudDisks(Map<String, Object> params)
    {
        return sqlSessionTemplate.selectList("cloudDiskMapper.getCloudDisks",params);
    }

    @Override
    public void updateCloudDisk(Map<String, Object> params)
    {
        sqlSessionTemplate.update("cloudDiskMapper.updateCloudDisk",params);
    }

    @Override
    public int addCloadDatabase(CloudDataBaseBO dataBaseBO)
    {
        return sqlSessionTemplate.insert("cloudDiskMapper.addCloadDatabase",dataBaseBO);
    }

    @Override
    public List<CloudDataBaseBO> getCloudDatabases(Map<String, Object> param)
    {
        return sqlSessionTemplate.selectList("cloudDiskMapper.getCloudDatabases",param);
    }

    @Override
    public void updateCloudDatabase(Map<String, Object> params)
    {
        sqlSessionTemplate.update("cloudDiskMapper.updateCloudDatabase",params);
    }

    @Override
    public void deleteDiskByTime() {
        sqlSessionTemplate.delete("cloudDiskMapper.deleteDiskByTime");
    }

    @Override
    public void deleteDBByTime() {
        sqlSessionTemplate.delete("cloudDiskMapper.deleteDBByTime");
    }
}
