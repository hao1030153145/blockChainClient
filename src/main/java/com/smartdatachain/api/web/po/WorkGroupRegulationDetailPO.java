package com.smartdatachain.api.web.po;

import java.util.List;

public class WorkGroupRegulationDetailPO {

    private WorkGroupRegulationAttrPO attr;
    private List<String> script;
    private String pageType;
    private WorkGroupRegulationPagerPO pager;
    private List<WorkGroupRegulationFieldsPO> fields;

    public WorkGroupRegulationAttrPO getAttr() {
        return attr;
    }

    public void setAttr(WorkGroupRegulationAttrPO attr) {
        this.attr = attr;
    }

    public List<String> getScript() {
        return script;
    }

    public void setScript(List<String> script) {
        this.script = script;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public WorkGroupRegulationPagerPO getPager() {
        return pager;
    }

    public void setPager(WorkGroupRegulationPagerPO pager) {
        this.pager = pager;
    }

    public List<WorkGroupRegulationFieldsPO> getFields() {
        return fields;
    }

    public void setFields(List<WorkGroupRegulationFieldsPO> fields) {
        this.fields = fields;
    }
}
