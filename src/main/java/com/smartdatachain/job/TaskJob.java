package com.smartdatachain.job;

import com.jeeframework.jeetask.task.Job;
import com.jeeframework.jeetask.task.context.JobContext;
import com.jeeframework.logicframework.util.logging.LoggerUtil;

/**
 * 包: com.smartdatachain.job
 * 源文件:TaskJob.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月31日
 */
public class TaskJob implements Job
{
    @Override
    public void doJob(JobContext jobContext)
    {
        try
        {
            JeeTask task = (JeeTask) jobContext.getTask();
            TaskFactory factory = TaskFactory.getInstance();
            LoggerUtil.infoTrace(" == taskJob addTask ==:");
            long start = System.currentTimeMillis();
            boolean flag = factory.addTask(task);
            while (!flag){
                Thread.sleep(500);
                flag = factory.addTask(task);
            }
            long end = System.currentTimeMillis();
            LoggerUtil.infoTrace(" == taskJob addTask ==耗时："+(end - start) );
        }catch (Exception e){}
    }
}
