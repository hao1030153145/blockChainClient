package com.smartdatachain.api.biz.service.impl.local;

import com.jeeframework.logicframework.biz.service.BaseService;
import com.smartdatachain.api.biz.service.CloudFileService;
import com.smartdatachain.api.integration.CloudFileDataService;
import com.smartdatachain.api.integration.bo.CloudFileBO;
import com.smartdatachain.api.integration.bo.CrawlDataBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.biz.service.impl.local
 * 源文件:CloudFileServicePojo.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
@Service("cloudFileService")
public class CloudFileServicePojo extends BaseService implements
        CloudFileService
{
    @Resource
    private CloudFileDataService cloudFileDataService;

    @Override
    public int addCloudFile(CloudFileBO cloudFileBO)
    {
        return cloudFileDataService.addCloudFile(cloudFileBO);
    }

    @Override
    public List<CloudFileBO> getCloudFiles(String cloudId)
    {
        Map<String,Object> param =new HashMap<>();
        param.put("cloudId",Integer.parseInt(cloudId));
        return cloudFileDataService.getCloudFiles(param);
    }

    @Override
    public List<CloudFileBO> getCloudFiles(String cloudId, int page, int size)
    {
        Map<String,Object> param =new HashMap<>();
        param.put("cloudId",Integer.parseInt(cloudId));
        param.put("page",page);
        param.put("size",size);
        return cloudFileDataService.getCloudFiles(param);
    }

    @Override
    public int getCloudFileCount(int cloudId)
    {
        return cloudFileDataService.getCloudFileCount(cloudId);
    }

    @Override
    public List<CloudFileBO> getCloudFiles(String[] ids)
    {
        List<Integer> listParam = new ArrayList<>();
        for (String idStr:ids){
            listParam.add(Integer.parseInt(idStr));
        }
        return cloudFileDataService.getCloudFiles(listParam);
    }

    @Override
    public int getCrawlDataCount(String tablename,int databaseId)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("tableName",tablename);
        param.put("databaseId",databaseId);
        return cloudFileDataService.getCrawlDataCount(param);
    }

    @Override
    public List<CrawlDataBO> getCrawlDataList(String tablename,int databaseId, int page,
            int size)
    {
        Map<String,Object> param = new HashMap<>();
        param.put("tableName",tablename);
        param.put("databaseId",databaseId);
        param.put("page",page);
        param.put("size",size);
        return cloudFileDataService.getCrawlDataList(param);
    }

    @Override
    public int addCrawlData(String tableName, CrawlDataBO crawlDataBO)
    {
        if (tableName.equalsIgnoreCase("crawl_data"))
            return cloudFileDataService.addCrawlDataInCrwlDataBo(crawlDataBO);
        else
            return cloudFileDataService.addDataInCrawlDataNews(crawlDataBO);
    }
}
