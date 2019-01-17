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
import com.smartdatachain.api.biz.service.DappService;
import com.smartdatachain.api.integration.bo.DappBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailsBO;
import com.smartdatachain.api.util.UUIDUtil;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.smartdatachain.api.web.po.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("dappController")
@Api(value = "dapp接口", description = "dapp相关接口", position = 2)
@RequestMapping("/dapp")
public class DappController {

    @Resource
    private DappService dappService;

    @RequestMapping(value = "/createDapp.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建dapp ", notes = "", position = 0)
    public CommonPO createWallet(@RequestParam(value = "dappName", required = true) @ApiParam(value = "dapp名字", required = true) String dappName,
                                 @RequestParam(value = "dappIntro", required = false) @ApiParam(value = "dapp描述", required = false) String dappIntro,
                                 @RequestParam(value = "macAddress", required = true) @ApiParam(value = "mac地址", required = true) String macAddress,
                                 HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(dappName)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("dappName", dappName);
        param.put("dappIntro", dappIntro);
        String token = UUIDUtil.generateShortUuid();
        param.put("token", token);
        param.put("macAddress", macAddress);
        System.out.println("创建dapp是请求的ip地址=====" + macAddress);

        Integer createDappResultInt = dappService.createDapp(param);
        if (createDappResultInt < 0) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/getDappByMacAddress.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据mac地址获得dapp列表数据 ", notes = "", position = 0)
    public List<DappPO> getDappByMacAddress(@RequestParam(value = "macAddress", required = true) @ApiParam(value = "mac地址", required = true) String macAddress,
                                            HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();

        param.put("macAddress", macAddress);
        System.out.println("获得dapp是请求的mac地址=====" + macAddress);

        List<DappBO> dappBOS = dappService.getDappByMacAddress(param);

        List<DappPO> dappPOList = new ArrayList<>();
        for (DappBO dappBO : dappBOS) {
            DappPO dappPO = new DappPO();
            List<DataBaseDetailBO> dataBaseDetailBOList = dappService.getDataBaseDetailByDappId(dappBO.getId());

            List<String> list = new ArrayList<>();
            for (DataBaseDetailBO dataBaseDetailBO : dataBaseDetailBOList) {
                Map<String, Object> map = new HashMap<>();
                map.put("dappId", dataBaseDetailBO.getId());
                map.put("dataBaseId", dataBaseDetailBO.getDataBaseId());
                List<DataBaseDetailsBO> dataBaseDetailsBO = dappService.getDataBaseDetailByDappIdAndDBId(map);
                if (dataBaseDetailsBO != null) {
                    for (DataBaseDetailsBO dataBaseDetailsBO1 : dataBaseDetailsBO) {
                        String dataBaseName = dataBaseDetailsBO1.getDataBaseName();
                        String walletId = dataBaseDetailsBO1.getWalletId();
                        String dataBasePassword = dataBaseDetailsBO1.getDataBasePassword();
                        String dataBaseTableName = dataBaseDetailsBO1.getDataBaseTableName();
                        int id = dataBaseDetailsBO1.getId();
                        list.add(dataBaseTableName + "-" + dataBaseName);
                    }
                }
            }
            dappPO.setId(dappBO.getId());
            dappPO.setMacAddress(dappBO.getMacAddress());
            dappPO.setDappName(dappBO.getDappName());
            dappPO.setDappIntro(dappBO.getDappIntro());
            dappPO.setDappRelevantTable(list);
            dappPOList.add(dappPO);
        }
        return dappPOList;
    }

    @RequestMapping(value = "/getDappDetailByDappId.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据dappId获得dapp的详细信息 ", notes = "", position = 0)
    public DataBaseDetailsPO getDapp(@RequestParam(value = "dappId", required = true) @ApiParam(value = "dappId", required = true) String dappId,
                                     HttpServletRequest req, HttpServletResponse res) {

        if (Validate.isEmpty(dappId)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }

        Integer dappIdInt = Integer.parseInt(dappId);

        List<DataBaseDetailBO> dataBaseDetailBOList = dappService.getDataBaseDetailByDappId(dappIdInt);

        List<DataBaseDetailsBO> list = new ArrayList<>();

        int id = 0;
        String dappName = null;
        String dappIntro = null;
        String macAddress = null;
        String token = null;
        DataBaseDetailsPO dataBaseDetailsPO = new DataBaseDetailsPO();
        for (DataBaseDetailBO dataBaseDetailBO : dataBaseDetailBOList) {
            id = dataBaseDetailBO.getId();
            dappName = dataBaseDetailBO.getDappName();
            dappIntro = dataBaseDetailBO.getDappIntro();
            macAddress = dataBaseDetailBO.getMacAddress();
            token = dataBaseDetailBO.getToken();

            Map<String, Object> map = new HashMap<>();
            map.put("dappId", dataBaseDetailBO.getId());
            map.put("dataBaseId", dataBaseDetailBO.getDataBaseId());
            List<DataBaseDetailsBO> dataBaseDetailsBO = dappService.getDataBaseDetailByDappIdAndDBId(map);
            dataBaseDetailsPO.setDataBaseDetailsBOS(dataBaseDetailsBO);

        }
        dataBaseDetailsPO.setId(id);
        dataBaseDetailsPO.setDappName(dappName);
        dataBaseDetailsPO.setDappIntro(dappIntro);
        dataBaseDetailsPO.setMacAddress(macAddress);
        dataBaseDetailsPO.setToken(token);


        if (dataBaseDetailBOList.size() < 1) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        return dataBaseDetailsPO;
    }


    @RequestMapping(value = "/dappRelevantDB.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "dapp与数据库关联 ", notes = "", position = 0)
    public CommonPO createWallet(@RequestParam(value = "dappId", required = true) @ApiParam(value = "dappId", required = true) String dappId,
                                 @RequestParam(value = "dataBaseId", required = true) @ApiParam(value = "数据库id", required = true) String dataBaseId,
                                 @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "钱包地址", required = true) String walletAddress,
                                 @RequestParam(value = "dataBaseName", required = true) @ApiParam(value = "数据库名字", required = true) String dataBaseName,
                                 @RequestParam(value = "dataBaseTableName", required = true) @ApiParam(value = "数据库表名", required = true) String dataBaseTableName,
                                 @RequestParam(value = "dataBaseToken", required = true) @ApiParam(value = "数据库密码", required = true) String dataBaseToken,
                                 HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> param1 = new HashMap<>();

        if (Validate.isEmpty(walletAddress) && Validate.isEmpty(dataBaseName) && Validate.isEmpty(dataBaseTableName) && Validate.isEmpty(dataBaseToken)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("walletAddress", walletAddress);
        param.put("dataBaseName", dataBaseName);
        param.put("dataBaseToken", dataBaseToken);
        //根据条件获得是否有数据，如果有数据，则可以关联，否则抛出异常
        Integer createDappResultInt = dappService.getDataBaseIdByParam(param);

        if (Validate.isEmpty(dappId)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param1.put("dappId", dappId);
        param1.put("dataBaseId", dataBaseId);
        param1.put("dataBaseTableName", dataBaseTableName);
        if (createDappResultInt != null) {
            // 根据条件添加中间表联系
            dappService.dappRelevantDB(param1);
        } else {
            throw new WebException(MySystemCode.RELEVENT_EXCEPTION_VISPROJECT_ERROR);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

/*    @RequestMapping(value = "/updateRelevantDBStatus.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改dapp关联数据库表的状态，如果dappId传值为开启连接，如果dappId不传值则为关闭连接 ", notes = "", position = 0)
    public CommonPO updateRelevantDBStatus(@RequestParam(value = "dappId", required = false) @ApiParam(value = "dappId", required = false) String dappId,
                                           @RequestParam(value = "dataBaseTableId", required = true) @ApiParam(value = "数据库表id", required = true) String dataBaseTableId,
                                           HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(dataBaseTableId)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("dataBaseTableId", dataBaseTableId);
        param.put("dappId", dappId);

        Integer updateRelevantDBStatusResultInt = dappService.updateRelevantDBStatus(param);

        if (updateRelevantDBStatusResultInt < 0) {
            throw new WebException(SystemCode.SYS_CONTROLLER_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }*/

    @RequestMapping(value = "/deleteDappRelevantByDappId.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "逻辑删除dapp关联的数据库==就是删除中间关系表中数据 ", notes = "", position = 0)
    public CommonPO deleteDappRelevantByDappId(@RequestParam(value = "dappId", required = true) @ApiParam(value = "dappId", required = true) String dappId,
                                               @RequestParam(value = "dataBaseId", required = true) @ApiParam(value = "dataBaseId数据库id", required = true) String dataBaseId,
                                               HttpServletRequest req, HttpServletResponse res) {
        /*
         * 逻辑删除dapp关联的数据库
         * 根据dappId和数据库id删除中间表数据
         * */
        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(dappId) && Validate.isEmpty(dataBaseId)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("dappId", dappId);
        param.put("dataBaseId", dataBaseId);
        int deleteDappRelevantByParamResultInt = dappService.deleteDappRelevantByParam(param);
        if (deleteDappRelevantByParamResultInt < 0) {
            throw new WebException(MySystemCode.DATA_EXCEPTION_RELEAVLE_ERROR);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/getDataBaseListByWalletAddress.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据钱包地址获得数据库列表 ", notes = "", position = 0)
    public List<DataBasePO> createWallet(@RequestParam(value = "walletAddress", required = true) @ApiParam(value = "钱包地址", required = true) String walletAddress,
                                         HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(walletAddress)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("walletAddress", walletAddress);
        List<DataBasePO> dataBasePOS = dappService.getDataBaseListByWalletAddress(param);
        if (dataBasePOS == null) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        return dataBasePOS;
    }

    @RequestMapping(value = "/deleteDappById.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据dappId删除dapp ", notes = "", position = 0)
    public CommonPO deleteDappById(@RequestParam(value = "dappId", required = true) @ApiParam(value = "dappId", required = true) String dappId,
                                   HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(dappId)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        param.put("dappId", dappId);
        Integer dappIdInt = Integer.parseInt(dappId);
        // 根据dappId删除dapp信息
        int deleteDappByIdResultInt = dappService.deleteDappById(dappIdInt);
        // 根据dappId 逻辑删除 关联的数据库表
        int deleteDappRelevantByParamResultInt = dappService.deleteDappRelevantByParam(param);
        if (deleteDappByIdResultInt < 0 && deleteDappRelevantByParamResultInt < 0) {
            throw new WebException(MySystemCode.BIZ_DELETE_EXCEPTION);
        }
        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }

    @RequestMapping(value = "/getDataToDapp.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "为dapp提供数据", notes = "", position = 0)
    public DappDataListPO getDataToDapp(@RequestParam(value = "dataBaseTableName", required = true) @ApiParam(value = "数据库表名", required = true) String dataBaseTableName,
                                        @RequestParam(value = "dataBaseName", required = true) @ApiParam(value = "数据库名字", required = true) String dataBaseName,
                                        @RequestParam(value = "type", required = true) @ApiParam(value = "数据类型", required = true) String type,
                                        @RequestParam(value = "size", required = false) @ApiParam(value = "查询记录数 默认 10 条", required = false) String size,
                                        @RequestParam(value = "page", required = false) @ApiParam(value = "查询页数 默认第 1 页", required = false) String page,
                                        HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> param = new HashMap<>();
        if (Validate.isEmpty(dataBaseTableName) || Validate.isEmpty(type) || Validate.isEmpty(dataBaseName)) {
            throw new WebException(SystemCode.SYS_REQUEST_EXCEPTION);
        }
        if ("news_data_1".equals(dataBaseTableName)) {
            dataBaseTableName = "crawl_data";
        }
        if ("news_data_2".equals(dataBaseTableName)) {
            dataBaseTableName = "crawl_data_news";
        }
        // 首先根据数据库名字获得数据库id
        Integer dataBaseId = dappService.getDataBaseIdByName(dataBaseName);
        //在根据下面是三个条件获得数据

        int sizeInt = 10;
        if (!Validate.isEmpty(size) && size.matches("\\d+")) {
            sizeInt = Integer.parseInt(size);
        }
        int pageInt = 0;
        if (!Validate.isEmpty(page) && page.matches("\\d+")) {
            pageInt = Integer.parseInt(page);
            pageInt = (pageInt - 1) * sizeInt;
        }
        param.put("size", sizeInt);
        param.put("page", pageInt);
        param.put("type", type);
        param.put("dataBaseId", dataBaseId);
        param.put("dataBaseTableName", dataBaseTableName);
        List<DappDataPO> dappDataPOS = dappService.getDataToDappByParam(param);
        int count = dappService.getDataToDappCountByParam(param);
        // 根据dappId 逻辑删除 关联的数据库表
        if (dappDataPOS == null) {
            throw new WebException(MySystemCode.BIZ_DATA_QUERY_EXCEPTION);
        }
        DappDataListPO dappDataListPO = new DappDataListPO();
        dappDataListPO.setCount(count);
        dappDataListPO.setDappDataPOS(dappDataPOS);
        return dappDataListPO;
    }

}
