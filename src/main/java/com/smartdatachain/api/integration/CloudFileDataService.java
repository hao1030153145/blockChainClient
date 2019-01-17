package com.smartdatachain.api.integration;

import com.jeeframework.logicframework.integration.DataService;
import com.smartdatachain.api.integration.bo.CloudFileBO;
import com.smartdatachain.api.integration.bo.CrawlDataBO;

import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.integration
 * 源文件:CloudFileDataService.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
public interface CloudFileDataService extends DataService
{
    int addCloudFile(CloudFileBO cloudFileBO);

    List<CloudFileBO> getCloudFiles(Map<String,Object> param);

    int getCloudFileCount(int cloudId);

    List<CloudFileBO> getCloudFiles(List<Integer> param);

    int getCrawlDataCount(Map<String,Object> param);

    List<CrawlDataBO> getCrawlDataList(Map<String,Object> param);

    int addCrawlDataInCrwlDataBo (CrawlDataBO crawlDataBO);

    int addDataInCrawlDataNews(CrawlDataBO crawlDataBO);
}
