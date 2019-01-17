package com.smartdatachain.api.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 包: com.smartdatachain.api.util
 * 源文件:DataTempFactory.java
 *
 * @author Allen ,Inc. All rights reserved.2018年11月02日
 */
public class DataTempFactory
{
    private static DataTempFactory dataTempFactory;

    private static ConcurrentHashMap<String,Object> tempContent = new ConcurrentHashMap<>();

    private DataTempFactory()
    {
    }

    public static DataTempFactory getInstice(){
        if (dataTempFactory == null)
            dataTempFactory = new DataTempFactory();
        return dataTempFactory;
    }

    public void add(String mark,String content){
        if (tempContent.containsKey(mark)){
            tempContent.replace(mark,content);
        }else{
            tempContent.put(mark,content);
        }
    }

    public String get(String mark){
        if (tempContent.containsKey(mark))
            return tempContent.remove(mark).toString();
        else
            return "";
    }
}
