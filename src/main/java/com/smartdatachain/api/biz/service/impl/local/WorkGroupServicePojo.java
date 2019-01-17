package com.smartdatachain.api.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.smartdatachain.api.biz.service.WorkGroupService;
import com.smartdatachain.api.integration.WorkGroupDataService;
import com.smartdatachain.api.integration.bo.WorkGroupBO;
import com.smartdatachain.api.integration.bo.WorkGroupDetailBO;
import com.smartdatachain.api.web.filter.WorkGroupDetailFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author haolen
 * @version 1.0
 */
@Service("workGroupService")
public class WorkGroupServicePojo extends BaseService implements WorkGroupService {

    @Resource
    private WorkGroupDataService contentTypeDataService;


    @Override
    public List<WorkGroupBO> getWorkGroupList(Map<String, Object> param) throws BizException {
        return contentTypeDataService.getWorkGroupList(param);
    }

    @Override
    public List<Integer>  getWordGroupIdByName(String string) throws BizException {
        return contentTypeDataService.getWordGroupIdByName(string);
    }

    @Override
    public Integer addWorkGroup(Map<String, Object> param) throws BizException {
        return contentTypeDataService.addWorkGroup(param);
    }

    @Override
    public Integer deleteWorkGroupById(Map<String, Object> param) throws BizException {
        return contentTypeDataService.deleteWorkGroupById(param);
    }

    @Override
    public Integer updateWorkGroupById(Map<String, Object> param) throws BizException {
        return contentTypeDataService.updateWorkGroupById(param);
    }

    @Override
    public Integer deleteWorkGroupDetailById(Map<String, Object> param) throws BizException {
        return contentTypeDataService.deleteWorkGroupDetailById(param);
    }

    @Override
    public List<WorkGroupDetailBO> getWorkGroupDetailByParam(String taskName, Integer collectionIdInt, String createTime, String endTime, Integer size, Integer page) throws BizException {
        return contentTypeDataService.getWorkGroupDetailByParam(taskName, collectionIdInt, createTime, endTime, size, page);
    }

    @Override
    public Integer getWorkGroupDetailCountByParam(String taskName, Integer collectionIdInt, String createTime, String endTime) throws BizException {
        return contentTypeDataService.getWorkGroupDetailCountByParam(taskName, collectionIdInt, createTime, endTime);
    }

    @Override
    public Integer updateWorkGroupDetailById(WorkGroupDetailFilter workGroupDetailFilter) throws BizException {
        return contentTypeDataService.updateWorkGroupDetailById(workGroupDetailFilter);
    }

    @Override
    public WorkGroupDetailBO getWorkGroupDetailById(String appId) throws BizException {
        return contentTypeDataService.getWorkGroupDetailById(appId);
    }

    @Override
    public Integer saveWorkGroupDetail(WorkGroupDetailFilter workGroupDetailFilter) throws BizException {
        return contentTypeDataService.saveWorkGroupDetail(workGroupDetailFilter);
    }

}