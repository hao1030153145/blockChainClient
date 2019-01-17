package com.smartdatachain.api.integration.impl.ibatis;

import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.smartdatachain.api.integration.CloudFileDataService;
import com.smartdatachain.api.integration.bo.CloudFileBO;
import com.smartdatachain.api.integration.bo.CrawlDataBO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.integration.impl.ibatis
 * 源文件:CloudFileDAOIbatis.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
@Scope("prototype")
@Repository("cloudFileDataService")
public class CloudFileDAOIbatis extends BaseDaoiBATIS implements
        CloudFileDataService
{
    @Override
    public int addCloudFile(CloudFileBO cloudFileBO)
    {
        return sqlSessionTemplate.insert("cloudFileMapper.addCloudFile",cloudFileBO);
    }

    @Override
    public List<CloudFileBO> getCloudFiles(Map<String,Object> param)
    {
        return sqlSessionTemplate.selectList("cloudFileMapper.getCloudFiles",param);

    }

    @Override
    public int getCloudFileCount(int cloudId)
    {
        return sqlSessionTemplate.selectOne("cloudFileMapper.getCloudFileCount",cloudId);
    }

    @Override
    public List<CloudFileBO> getCloudFiles(List<Integer> param)
    {
        return sqlSessionTemplate.selectList("cloudFileMapper.getCloudFilesByIds",param);
    }

    @Override
    public int getCrawlDataCount(Map<String, Object> param)
    {
        return sqlSessionTemplate.selectOne("cloudFileMapper.getCrawlDataCount",param);
    }

    @Override
    public List<CrawlDataBO> getCrawlDataList(Map<String, Object> param)
    {
        return sqlSessionTemplate.selectList("cloudFileMapper.getCrawlDataList",param);
    }

    @Override
    public int addCrawlDataInCrwlDataBo(CrawlDataBO crawlDataBO)
    {
        return sqlSessionTemplate.insert("cloudFileMapper.addCrawlDataInCrwlDataBo",crawlDataBO);
    }

    @Override
    public int addDataInCrawlDataNews(CrawlDataBO crawlDataBO)
    {
        return sqlSessionTemplate.insert("cloudFileMapper.addDataInCrawlDataNews",crawlDataBO);
    }
}
