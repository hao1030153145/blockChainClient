package com.smartdatachain.api.biz.service;

import com.jeeframework.logicframework.biz.service.BizService;
import com.smartdatachain.api.integration.bo.CloudFileBO;
import com.smartdatachain.api.integration.bo.CrawlDataBO;

import java.util.List;

/**
 * 包: com.smartdatachain.api.biz.service
 * 源文件:CloudFileService.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public interface CloudFileService extends BizService
{
    int addCloudFile(CloudFileBO cloudFileBO);

    List<CloudFileBO> getCloudFiles(String cloudId);

    int getCloudFileCount(int cloudId);

    List<CloudFileBO> getCloudFiles(String cloudId,int page,int size);

    List<CloudFileBO> getCloudFiles(String[] ids);

    int getCrawlDataCount(String tablename,int databaseId);

    List<CrawlDataBO> getCrawlDataList(String tablename,int databaseId,int page,int size);

    int addCrawlData(String tableName,CrawlDataBO crawlDataBO);
}
