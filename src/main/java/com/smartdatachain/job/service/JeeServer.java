package com.smartdatachain.job.service;

import com.jeeframework.jeetask.startup.JeeTaskServer;

/**
 * 包: com.smartdatachain.job.service
 * 源文件:JeeServer.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月30日
 */
public class JeeServer extends JeeTaskServer
{
    public JeeServer()
    {
        try
        {
            properties.setProperty("task.jobEventProcessorClass",
                    "com.jeeframework.jeetask.event.rdb.impl.JobEventCommonStorageProcessor");

        }catch (Exception e){}
    }
}
