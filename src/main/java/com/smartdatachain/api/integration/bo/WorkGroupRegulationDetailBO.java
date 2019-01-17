package com.smartdatachain.api.integration.bo;

import com.smartdatachain.api.web.po.WorkGroupRegulationAttrPO;
import com.smartdatachain.api.web.po.WorkGroupRegulationFieldsPO;
import com.smartdatachain.api.web.po.WorkGroupRegulationPagerPO;

public class WorkGroupRegulationDetailBO {


    private WorkGroupRegulationAttrPO attr;
    private String script;
    private String pageType;
    private WorkGroupRegulationPagerPO pager;
    private WorkGroupRegulationFieldsPO fields;

    public WorkGroupRegulationAttrPO getAttr() {
        return attr;
    }

    public void setAttr(WorkGroupRegulationAttrPO attr) {
        this.attr = attr;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
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

    public WorkGroupRegulationFieldsPO getFields() {
        return fields;
    }

    public void setFields(WorkGroupRegulationFieldsPO fields) {
        this.fields = fields;
    }
}
