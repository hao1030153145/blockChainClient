package com.smartdatachain.job;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 包: com.smartdatachain.job
 * 源文件:TaskUtil.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月31日
 */
public class TaskFactory
{
    private static TaskFactory taskFactory;

    private static ConcurrentLinkedQueue<JeeTask> Queue = new ConcurrentLinkedQueue<>();

    private TaskFactory(){}

    public static TaskFactory getInstance(){
        if (taskFactory ==null)
            taskFactory = new TaskFactory();
        return taskFactory;
    }

    public synchronized JeeTask getTaskINFO(){
        if (Queue.size()>0){
            return Queue.poll();
        }
        return null;
    }

    public synchronized boolean addTask(JeeTask task)
            throws InterruptedException
    {
            if(Queue.size()<3){
                Queue.add(task);
                return true;
            }else{
                return false;
            }
    }



}
