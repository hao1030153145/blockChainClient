/**
 * @project: api
 * @Title: UserController.java
 * @Package: com.smartdatachain.api.web.controller
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.smartdatachain.api.web.controller;

import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.smartdatachain.api.web.po.CommonPO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Controller("dataExportController")
@Api(value = "数据导出接口", description = "数据导出相关接口", position = 2)
@RequestMapping("/dataExport")
public class DataExportController {

    // @Resource
    //private DataExportService dataExportService;

    @RequestMapping(value = "/dbConnectTest.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "数据库连接测试 ", notes = "", position = 0)
    public CommonPO createWallet(@RequestParam(value = "dataBaseType", required = true) @ApiParam(value = "数据库类型", required = true) String dataBaseType,
                                 @RequestParam(value = "server", required = true) @ApiParam(value = "服务器", required = true) String server,
                                 @RequestParam(value = "port", required = true) @ApiParam(value = "端口号", required = true) String port,
                                 @RequestParam(value = "userName", required = true) @ApiParam(value = "用户名", required = true) String userName,
                                 @RequestParam(value = "password", required = true) @ApiParam(value = "密码", required = true) String password,
                                 @RequestParam(value = "dataBaseCode", required = true) @ApiParam(value = "数据库编码", required = true) String dataBaseCode,
                                 @RequestParam(value = "dataBaseName", required = true) @ApiParam(value = "数据库名称", required = true) String dataBaseName,
                                 HttpServletRequest req, HttpServletResponse res) {
        Connection conn;
        String myDriver = null;
        String url = null;
        if (("oracle").equals(dataBaseType)) {
            myDriver="oracle.jdbc.driver.OracleDriver";
            url = "jdbc:oracle:thin:@" + server + "\\:" + port + "\\:" + dataBaseName;
        }
        if (("mysql").equals(dataBaseType)) {
            myDriver="com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + server + "\\:" + port + "\\/" + dataBaseName;
        }
        if (("sqlServer").equals(dataBaseType)) {
            myDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url = "jdbc:sqlserver://" + server + ":" + port + ";DatabaseName=" + dataBaseName + ";";
        }
        try {
            Class.forName(myDriver);
            conn = DriverManager.getConnection(url, userName, password);
            if (conn == null){
                throw  new WebException(MySystemCode.CONNT_EXCEPTION_VISPROJECT_ERROR);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        CommonPO commonPO = new CommonPO();
        commonPO.setCode(0);
        return commonPO;
    }


}
