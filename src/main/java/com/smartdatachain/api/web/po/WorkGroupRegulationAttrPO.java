package com.smartdatachain.api.web.po;

import java.util.List;
import java.util.Map;

public class WorkGroupRegulationAttrPO {


    private String listSelector;
    private Map<String,WorkGroupRegulationExtractFieldsPO> extractFields;
    private WorkGroupRegulationSettingsPO settings;
    private String session_id;
    private List<String> urls;

    public String getListSelector() {
        return listSelector;
    }

    public void setListSelector(String listSelector) {
        this.listSelector = listSelector;
    }

    public Map<String, WorkGroupRegulationExtractFieldsPO> getExtractFields() {
        return extractFields;
    }

    public void setExtractFields(Map<String, WorkGroupRegulationExtractFieldsPO> extractFields) {
        this.extractFields = extractFields;
    }

    public WorkGroupRegulationSettingsPO getSettings() {
        return settings;
    }

    public void setSettings(WorkGroupRegulationSettingsPO settings) {
        this.settings = settings;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
