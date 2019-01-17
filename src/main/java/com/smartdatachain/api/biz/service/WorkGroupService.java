package com.smartdatachain.api.biz.service;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BizService;
import com.smartdatachain.api.integration.bo.WorkGroupBO;
import com.smartdatachain.api.integration.bo.WorkGroupDetailBO;
import com.smartdatachain.api.web.filter.WorkGroupDetailFilter;

import java.util.List;
import java.util.Map;

/**
 * @author haolen
 * @version 1.0
 */
public interface WorkGroupService extends BizService {

    /**
     * 根据 map 查询 WorkGroupList
     *
     * @param param
     * @return
     * @throws BizException
     */
    List<WorkGroupBO> getWorkGroupList(Map<String, Object> param) throws BizException;

    /**
     * 根据任务组名字获得任务组id
     *
     * @param string
     * @return
     * @throws BizException
     */
    List<Integer>  getWordGroupIdByName(String string) throws BizException;

    /**
     * 添加任务组信息
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer addWorkGroup(Map<String, Object> param) throws BizException;

    /**
     * 根据id 删除任务组
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer deleteWorkGroupById(Map<String, Object> param) throws BizException;

    /**
     * 根据id 修改任务组名字
     *
     * @param param
     * @return
     * @throws BizException
     */
     Integer updateWorkGroupById(Map<String, Object> param) throws BizException;

    /**
     * 根据id 删除任务组下任务
     *
     * @param param
     * @return
     * @throws BizException
     */
    Integer deleteWorkGroupDetailById(Map<String, Object> param) throws BizException;

    /**
     * 根据 条件查询 WorkGroupDetail
     *
     * @return
     * @throws BizException
     */
    List<WorkGroupDetailBO> getWorkGroupDetailByParam(String name, Integer collectionId, String timeCreate, String endTime, Integer size, Integer page) throws BizException;

    /**
     * 根据 条件查询 WorkGroupDetailCount
     *
     * @return
     * @throws BizException
     */
    Integer getWorkGroupDetailCountByParam(String name, Integer collectionId, String timeCreate, String endTime) throws BizException;

    /**
     * 根据id修改任务组下详细任务名称和分组
     *
     * @param workGroupDetailFilter
     * @return
     * @throws BizException
     */
    Integer updateWorkGroupDetailById(WorkGroupDetailFilter workGroupDetailFilter) throws BizException;


    /**
     * 根据 appId 获得 WorkGroupDetail
     *
     * @return
     * @throws BizException
     */
    WorkGroupDetailBO getWorkGroupDetailById(String appId) throws BizException;

    /**
     * 保存任务组下的任务
     *
     * @param workGroupDetailFilter
     * @return
     * @throws BizException
     */
    Integer saveWorkGroupDetail(WorkGroupDetailFilter workGroupDetailFilter) throws BizException;

}