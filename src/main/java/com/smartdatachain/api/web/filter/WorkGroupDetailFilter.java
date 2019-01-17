package com.smartdatachain.api.web.filter;

public class WorkGroupDetailFilter {

    private int appId;
    private String info;
    private String name;
    private String type;
    private String status;
    private String graph;
    private String typeSub;
    private int countLocal;
    private int collectionId;
    private String rule;

    public WorkGroupDetailFilter(String info, String name, String type, String status, String graph, String typeSub, String rule) {
        this.info = info;
        this.name = name;
        this.type = type;
        this.status = status;
        this.graph = graph;
        this.typeSub = typeSub;
        this.rule = rule;
    }


    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
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

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getTypeSub() {
        return typeSub;
    }

    public void setTypeSub(String typeSub) {
        this.typeSub = typeSub;
    }

    public int getCountLocal() {
        return countLocal;
    }

    public void setCountLocal(int countLocal) {
        this.countLocal = countLocal;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
