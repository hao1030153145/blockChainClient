package com.smartdatachain.api.web.po;

import java.util.Date;

public class WorkGroupDetailPO {


    private int app_id;
    private String info;
    private String name;
    private String type;
    private String status;
    private Date time_create;
    //private WorkGroupRegulationDetailPO graph;
    private String graph;
    private String type_sub;
    private int count_local;
    private int collection_id;
    private String rule;


    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime_create() {
        return time_create;
    }

    public void setTime_create(Date time_create) {
        this.time_create = time_create;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getType_sub() {
        return type_sub;
    }

    public void setType_sub(String type_sub) {
        this.type_sub = type_sub;
    }

    public int getCount_local() {
        return count_local;
    }

    public void setCount_local(int count_local) {
        this.count_local = count_local;
    }

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
