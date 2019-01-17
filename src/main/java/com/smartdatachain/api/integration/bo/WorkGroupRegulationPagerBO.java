package com.smartdatachain.api.integration.bo;

public class WorkGroupRegulationPagerBO {


    private String action;
    private String type;
    private String clickedValue;
    private String selector;
    private String pageNum;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClickedValue() {
        return clickedValue;
    }

    public void setClickedValue(String clickedValue) {
        this.clickedValue = clickedValue;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
}
