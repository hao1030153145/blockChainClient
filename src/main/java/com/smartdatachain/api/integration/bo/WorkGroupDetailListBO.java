package com.smartdatachain.api.integration.bo;

import com.smartdatachain.api.web.po.WorkGroupDetailPO;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

public class WorkGroupDetailListBO {

    @ApiModelProperty(value = "任务组详细任务列表集合")
    private List<WorkGroupDetailPO> list;
    @ApiModelProperty(value = "任务组详细任务当前页码")
    private int page;
    @ApiModelProperty(value = "任务组详细任务列表数量")
    private int total;


    public List<WorkGroupDetailPO> getList() {
        return list;
    }

    public void setList(List<WorkGroupDetailPO> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
