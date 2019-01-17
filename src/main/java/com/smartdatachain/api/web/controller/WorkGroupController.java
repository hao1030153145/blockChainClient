/**
 * @project: api
 * @Title: UserController.java
 * @Package: com.smartdatachain.api.web.controller
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.api.web.controller;

import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.SystemCode;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.biz.service.WorkGroupService;
import com.smartdatachain.api.integration.bo.WorkGroupBO;
import com.smartdatachain.api.integration.bo.WorkGroupDetailBO;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.smartdatachain.api.web.filter.WorkGroupDetailFilter;
import com.smartdatachain.api.web.po.CommonPO;
import com.smartdatachain.api.web.po.WorkGroupDetailListPO;
import com.smartdatachain.api.web.po.WorkGroupDetailPO;
import com.smartdatachain.api.web.po.WorkGroupPO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller("workGroupController")
@Api(value = "首页任务组", description = "首页任务组相关接口", position = 2)
@RequestMapping("/workGroup")
public class WorkGroupController {

    @Resource
    private WorkGroupService workGroupService;

    @RequestMapping(value = "/getWorkGroup.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询任务组接口 ", notes = "", position = 0)
    public List<WorkGroupPO> getDataCrawlList(@RequestParam(value = "name", required = false) @ApiParam(value = "任务组名", required = false) String name,
                                              @RequestParam(value = "macAddress", required = true) @ApiParam(value = "mac地址", required = true) String macAddress,
                                              HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        if (name != null && !("").equals(name)) {
            param.put("name", name);
        }
        param.put("userId", macAddress);
        System.out.println("获取客户端的mac地址为》》》" + macAddress);

        List<WorkGroupPO> workGroupPOList = new ArrayList<>();

        List<WorkGroupBO> workGroupBOS = workGroupService.getWorkGroupList(param);

        if (workGroupBOS.size() == 0) {
            WorkGroupPO workGroupPOS1 = new WorkGroupPO();
            workGroupPOS1.setId("0");
            workGroupPOS1.setUser_id("0");
            workGroupPOS1.setName("默认分组");
            workGroupPOList.add(workGroupPOS1);
            return workGroupPOList;
        }

        for (WorkGroupBO workGroupBO : workGroupBOS) {
            WorkGroupPO workGroupPOS = new WorkGroupPO();
            workGroupPOS.setId(workGroupBO.getId());
            workGroupPOS.setUser_id(workGroupBO.getUserId());
            workGroupPOS.setName(workGroupBO.getName());
            workGroupPOList.add(workGroupPOS);
        }
        return workGroupPOList;
    }

    @RequestMapping(value = "/addWorkGroup.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加任务组 ", notes = "", position = 0)
    public Map<String, Object> addWorkGroup(@RequestParam(value = "name", required = true) @ApiParam(value = "任务组名", required = true) String name,
                                            @RequestParam(value = "macAddress", required = true) @ApiParam(value = "mac地址", required = true) String macAddress,
                                            HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (!Validate.isEmpty(name) || !Validate.isEmpty(macAddress)) {
            param.put("name", name);
            param.put("userId", macAddress);
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }

        System.out.println("获取客户端的mac地址为===" + macAddress);

        int addWorkGroupResultInt = workGroupService.addWorkGroup(param);
        List<Integer> wordGroupId = workGroupService.getWordGroupIdByName(name);
        if (addWorkGroupResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        int maxId = Collections.max(wordGroupId);
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("id", maxId);
        return map;
    }

//    @RequestMapping(value = "/updateWorkGroupDetailById.json", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "移动子任务分组 ", notes = "", position = 0)
//    public Map<String, Object> updateWorkGroupDetailById(@RequestParam(value = "name", required = true) @ApiParam(value = "任务组名", required = true) String name,
//                                            @RequestParam(value = "macAddress", required = true) @ApiParam(value = "mac地址", required = true) String macAddress,
//                                            HttpServletRequest req, HttpServletResponse res) {
//        Map<String, Object> param = new HashMap<>();
//        if (!Validate.isEmpty(name) || !Validate.isEmpty(macAddress)) {
//            param.put("name", name);
//            param.put("userId", macAddress);
//        } else {
//            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
//        }
//
//        System.out.println("获取客户端的mac地址为===" + macAddress);
//
//        int addWorkGroupResultInt = workGroupService.addWorkGroup(param);
//        List<Integer> wordGroupId = workGroupService.getWordGroupIdByName(name);
//        if (addWorkGroupResultInt < 0) {
//            throw new WebException(MySystemCode.ACTION_EXCEPTION);
//        }
//        int maxId = Collections.max(wordGroupId);
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "0");
//        map.put("id", maxId);
//        return map;
//    }

    @RequestMapping(value = "/deleteWorkGroupById.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据id删除任务组 ", notes = "", position = 0)
    public CommonPO deleteWorkGroupById(@RequestParam(value = "id", required = true) @ApiParam(value = "任务组id", required = true) String id,
                                        @RequestParam(value = "isDelete", required = false) @ApiParam(value = "是否删除 1为删除，0为不删除，默认为 0", required = false) String isDelete,
                                        HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (id != null && !("").equals(id)) {
            param.put("collectionId", Integer.parseInt(id));
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        int collectionIdInt = Integer.parseInt(id);
        int count = workGroupService.getWorkGroupDetailCountByParam(null, collectionIdInt, null, null);
        // 如果查询到的任务组下面存在子任务，且isDelete=0，则不删除，并返回提示
        String isDeletes;
        if (isDelete == null || "".equals(isDelete)) {
            isDeletes = "0";
        } else {
            isDeletes = isDelete;
        }
        if (count > 0 && ("0").equals(isDeletes)) {
            throw new WebException(MySystemCode.DELETE_EXCEPTION_VISPROJECT_ERROR);
        }
        int deleteWorkGroupByIdResultInt = workGroupService.deleteWorkGroupById(param);
        int deleteWorkGroupDetailByIdResultInt = workGroupService.deleteWorkGroupDetailById(param);
        if (deleteWorkGroupByIdResultInt < 0 && deleteWorkGroupDetailByIdResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/updateWorkGroupById.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据id修改任务组名称 ", notes = "", position = 0)
    public CommonPO updateWorkGroupById(@RequestParam(value = "id", required = true) @ApiParam(value = "任务组id", required = true) String id,
                                        @RequestParam(value = "name", required = true) @ApiParam(value = "任务组名", required = true) String name,
                                        HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (id != null && !("").equals(id)) {
            param.put("id", Integer.parseInt(id));
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        if (name != null && !("").equals(name)) {
            param.put("name", name);
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        int updateWorkGroupByIdResultInt = workGroupService.updateWorkGroupById(param);
        if (updateWorkGroupByIdResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/deleteWorkGroupDetailById.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据appId和collectionId删除任务组下详细任务 ", notes = "", position = 0)
    public CommonPO deleteWorkGroupDetailById(@RequestParam(value = "appId", required = true) @ApiParam(value = "任务id", required = true) String appId,
                                              @RequestParam(value = "collectionId", required = true) @ApiParam(value = "任务组id", required = true) String collectionId,
                                              HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (appId != null && !("").equals(appId)) {
            param.put("appId", Integer.parseInt(appId));
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        if (collectionId != null && !("").equals(collectionId)) {
            param.put("collectionId", collectionId);
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        int deleteWorkGroupDetailByIdResultInt = workGroupService.deleteWorkGroupDetailById(param);
        if (deleteWorkGroupDetailByIdResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/saveWorkGroupDetail.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存任务组下的子任务信息及其规则 ", notes = "", position = 0)
    public WorkGroupDetailPO saveWorkGroupDetail(@RequestParam(value = "info", required = false) @ApiParam(value = "任务简介", required = false) String info,
                                                 @RequestParam(value = "name", required = true) @ApiParam(value = "任务名称", required = true) String name,
                                                 @RequestParam(value = "type", required = false) @ApiParam(value = "任务类型", required = false) String type,
                                                 @RequestParam(value = "status", required = false) @ApiParam(value = "任务状态", required = false) String status,
                                                 @RequestParam(value = "graph", required = false) @ApiParam(value = "任务规则信息", required = false) String graph,
                                                 @RequestParam(value = "typeSub", required = false) @ApiParam(value = "任务提交类型", required = false) String typeSub,
                                                 @RequestParam(value = "countLocal", required = false) @ApiParam(value = "本地抓取数据量", required = false) String countLocal,
                                                 @RequestParam(value = "collectionId", required = false) @ApiParam(value = "任务组id", required = false) String collectionId,
                                                 @RequestParam(value = "rule", required = false) @ApiParam(value = "任务信息", required = false) String rule,
                                                 HttpServletRequest req, HttpServletResponse res) {

        System.out.println("123");
        WorkGroupDetailFilter workGroupDetailFilter = new WorkGroupDetailFilter(info, name, type, status, graph, typeSub, rule);
        if (countLocal != null) {
            workGroupDetailFilter.setCountLocal(Integer.parseInt(countLocal));
        }
        if (collectionId != null) {
            workGroupDetailFilter.setCollectionId(Integer.parseInt(collectionId));
        }
        int saveWorkGroupDetailResultInt = workGroupService.saveWorkGroupDetail(workGroupDetailFilter);
        int appId =  workGroupDetailFilter.getAppId();
        if (saveWorkGroupDetailResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }
        WorkGroupDetailPO workGroupDetailPO = new WorkGroupDetailPO();
        // 把数据库的数据转换成对象
        //WorkGroupRegulationDetailPO workGroupRegulationDetailPO = JSON.parseObject(graph, WorkGroupRegulationDetailPO.class);
        workGroupDetailPO.setGraph(graph);
        workGroupDetailPO.setCount_local(countLocal == null ? 0 : Integer.parseInt(countLocal));
        workGroupDetailPO.setCollection_id(collectionId == null ? 0 : Integer.parseInt(collectionId));
        workGroupDetailPO.setName(name);
        workGroupDetailPO.setInfo(info);
        workGroupDetailPO.setRule(rule);
        workGroupDetailPO.setStatus(status);
        workGroupDetailPO.setType(type);
        workGroupDetailPO.setType_sub(typeSub);
        workGroupDetailPO.setApp_id(appId);
        workGroupDetailPO.setTime_create(new Date());
        return workGroupDetailPO;
    }

    @RequestMapping(value = "/updateWorkGroupDetailById.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据appId修改任务组下的子任务信息及其规则 ", notes = "", position = 0)
    public WorkGroupDetailPO updateWorkGroupDetailsById(@RequestParam(value = "appId", required = false) @ApiParam(value = "任务Id", required = false) String appId,
                                                        @RequestParam(value = "info", required = false) @ApiParam(value = "任务简介", required = false) String info,
                                                        @RequestParam(value = "name", required = false) @ApiParam(value = "任务名称", required = false) String name,
                                                        @RequestParam(value = "type", required = false) @ApiParam(value = "任务类型", required = false) String type,
                                                        @RequestParam(value = "status", required = false) @ApiParam(value = "任务状态", required = false) String status,
                                                        @RequestParam(value = "graph", required = false) @ApiParam(value = "任务规则信息", required = false) String graph,
                                                        @RequestParam(value = "typeSub", required = false) @ApiParam(value = "任务提交类型", required = false) String typeSub,
                                                        @RequestParam(value = "countLocal", required = false) @ApiParam(value = "本地抓取数据量", required = false) String countLocal,
                                                        @RequestParam(value = "collectionId", required = true) @ApiParam(value = "任务组id", required = true) String collectionId,
                                                        @RequestParam(value = "rule", required = false) @ApiParam(value = "任务信息", required = false) String rule,
                                                        HttpServletRequest req, HttpServletResponse res) {

        WorkGroupDetailFilter workGroupDetailFilter = new WorkGroupDetailFilter(info, name, type, status, graph, typeSub, rule);
        if (appId != null) {
            workGroupDetailFilter.setAppId(Integer.parseInt(appId));
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        if (countLocal != null) {
            workGroupDetailFilter.setCountLocal(Integer.parseInt(countLocal));
        }else {
            workGroupDetailFilter.setCountLocal(-1);
        }
        if (collectionId != null) {
            workGroupDetailFilter.setCollectionId(Integer.parseInt(collectionId));
        } else {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        int updateWorkGroupDetailsByIdResultInt = workGroupService.updateWorkGroupDetailById(workGroupDetailFilter);
        if (updateWorkGroupDetailsByIdResultInt < 0) {
            throw new WebException(MySystemCode.ACTION_EXCEPTION);
        }


        WorkGroupDetailBO workGroupDetailBO =  workGroupService.getWorkGroupDetailById(appId);

        WorkGroupDetailPO workGroupDetailPO = new WorkGroupDetailPO();
        // 把数据库的数据转换成对象
        //WorkGroupRegulationDetailPO workGroupRegulationDetailPO = JSON.parseObject(graph, WorkGroupRegulationDetailPO.class);
        workGroupDetailPO.setGraph(workGroupDetailBO.getGraph());
        workGroupDetailPO.setCount_local(workGroupDetailBO.getCountLocal());
        workGroupDetailPO.setCollection_id(workGroupDetailBO.getCollectionId());
        workGroupDetailPO.setName(workGroupDetailBO.getName());
        workGroupDetailPO.setInfo(workGroupDetailBO.getInfo());
        workGroupDetailPO.setRule(workGroupDetailBO.getRule());
        workGroupDetailPO.setStatus(workGroupDetailBO.getStatus());
        workGroupDetailPO.setType(workGroupDetailBO.getType());
        workGroupDetailPO.setType_sub(workGroupDetailBO.getTypeSub());
        workGroupDetailPO.setApp_id(workGroupDetailBO.getAppId());
        workGroupDetailPO.setTime_create(new Date());
        return workGroupDetailPO;


    }

    @RequestMapping(value = "/getWorkGroupDetailByParam.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询任务组下任务详细信息及其规则 接口 ", notes = "", position = 0)
    public WorkGroupDetailListPO getWorkGroupDetailByParam(@RequestParam(value = "taskName", required = false) @ApiParam(value = "任务名", required = false) String taskName,
                                                           @RequestParam(value = "collectionId", required = true) @ApiParam(value = "任务组id", required = true) String collectionId,
                                                           @RequestParam(value = "timeCreate", required = false) @ApiParam(value = "任务创建时间", required = false) String timeCreate,
                                                           @RequestParam(value = "size", required = false) @ApiParam(value = "查询记录数 默认 10 条", required = false) String size,
                                                           @RequestParam(value = "page", required = false) @ApiParam(value = "查询页数 默认第 1 页", required = false) String page,
                                                           HttpServletRequest req, HttpServletResponse res) {
        if (collectionId == null || "".equals(collectionId) || !collectionId.matches("\\d+")) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        Integer collectionIdInt = Integer.parseInt(collectionId);

        int sizeInt = 10;
        if (!Validate.isEmpty(size) && size.matches("\\d+")) {
            sizeInt = Integer.parseInt(size);
        }
        int pageInt = 0;
        if (!Validate.isEmpty(page) && page.matches("\\d+")) {
            pageInt = Integer.parseInt(page);
            pageInt = (pageInt - 1) * sizeInt;
        } else {
            page = "1";
        }

        String endTime = "";
        if (!Validate.isEmpty(timeCreate)) {
            endTime = timeCreate + " 23:59:59";
            timeCreate = timeCreate + " 00:00:00";
        }

        List<WorkGroupDetailBO> workGroupDetailBOS = workGroupService.getWorkGroupDetailByParam(taskName, collectionIdInt, timeCreate, endTime, sizeInt, pageInt);
        int count = workGroupService.getWorkGroupDetailCountByParam(taskName, collectionIdInt, timeCreate, endTime);

        if (workGroupDetailBOS.size() == 0 || count == 0) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }

        WorkGroupDetailListPO workGroupDetailListPO = new WorkGroupDetailListPO();

        List<WorkGroupDetailPO> workGroupDetailPOList = new ArrayList<>();
        for (WorkGroupDetailBO workGroupDetailBO : workGroupDetailBOS) {
            WorkGroupDetailPO workGroupDetailPO = new WorkGroupDetailPO();

            workGroupDetailPO.setApp_id(workGroupDetailBO.getAppId());
            workGroupDetailPO.setCollection_id(workGroupDetailBO.getCollectionId());
            workGroupDetailPO.setCount_local(workGroupDetailBO.getCountLocal());
            workGroupDetailPO.setName(workGroupDetailBO.getName());
            workGroupDetailPO.setInfo(workGroupDetailBO.getInfo());
            workGroupDetailPO.setRule(workGroupDetailBO.getRule());
            workGroupDetailPO.setStatus(workGroupDetailBO.getStatus());
            workGroupDetailPO.setType(workGroupDetailBO.getType());
            workGroupDetailPO.setType_sub(workGroupDetailBO.getTypeSub());
            workGroupDetailPO.setTime_create(workGroupDetailBO.getTimeCreate());
            // 把数据库的数据转换成对象
            //WorkGroupRegulationDetailPO workGroupRegulationDetailPO = JSON.parseObject(workGroupDetailBO.getGraph(), WorkGroupRegulationDetailPO.class);
            workGroupDetailPO.setGraph(workGroupDetailBO.getGraph());
            // 最后将整合的数据放在集合中
            workGroupDetailPOList.add(workGroupDetailPO);
        }
        workGroupDetailListPO.setList(workGroupDetailPOList);
        workGroupDetailListPO.setPage(Integer.parseInt(page));
        workGroupDetailListPO.setTotal(count);

        return workGroupDetailListPO;
    }


}
