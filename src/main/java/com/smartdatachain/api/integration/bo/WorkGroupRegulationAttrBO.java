package com.smartdatachain.api.integration.bo;

import com.smartdatachain.api.web.po.WorkGroupRegulationSettingsPO;

public class WorkGroupRegulationAttrBO {


    private String listSelector;
    private String extractFields;
    private WorkGroupRegulationSettingsPO settings;
    private String session_id;
    private String urls;

    public String getListSelector() {
        return listSelector;
    }

    public void setListSelector(String listSelector) {
        this.listSelector = listSelector;
    }

    public String getExtractFields() {
        return extractFields;
    }

    public void setExtractFields(String extractFields) {
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

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
