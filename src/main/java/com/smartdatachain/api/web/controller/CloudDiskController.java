package com.smartdatachain.api.web.controller;

import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.biz.service.CloudDisksService;
import com.smartdatachain.api.biz.service.CloudFileService;
import com.smartdatachain.api.biz.service.WalletService;
import com.smartdatachain.api.integration.bo.*;
import com.smartdatachain.api.util.ExcelUtil;
import com.smartdatachain.api.util.FileUtil;
import com.smartdatachain.api.util.eth.contract.TransferUtils;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 包: com.smartdatachain.api.web.controller
 * 源文件:CloudDiskController.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */


@Controller("cloudDiskController")
@Api(value = "云磁盘与数据库", position = 3)
@RequestMapping("/cloud")
public class CloudDiskController {
    @Resource
    private CloudDisksService cloudDisksService;
    @Resource
    private CloudFileService cloudFileService;
    @Resource
    private WalletService walletService;

    private final static double price = 0.002;


    @RequestMapping(value = "/addCloudDiskDB.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加云磁盘", position = 1)
    public Map addCloudDisk(@RequestParam(value = "diskName", required = true) @ApiParam(value = "磁盘名称", required = true) String diskName,
                            @RequestParam(value = "type", required = true) @ApiParam(value = "类型(disk-磁盘，db-数据库)", required = true) String type,
                            @RequestParam(value = "size", required = true) @ApiParam(value = "磁盘大小", required = true) String size,
                            @RequestParam(value = "useFulTime", required = true) @ApiParam(value = "购买日长", required = true) String useFulTime,
                            @RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
                            @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "地址", required = true) String walletAddress,
                            @RequestParam(value = "walletPublicKey", required = true) @ApiParam(value = "公钥", required = true) String walletPublicKey,
                            @RequestParam(value = "gasFee", required = true) @ApiParam(value = "矿工费", required = true) String gasFee) {
        Map<String, Object> resultMap = new HashMap<>();
        // 根据获得的gasFee进行转换，获得gasPrice和gasLimit
        Map<String, BigInteger> gasFeeMap = TransferUtils.covertZSLGasFee(gasFee);
        BigInteger gasPrice = gasFeeMap.get("gasPrice");
        BigInteger gasLimit = gasFeeMap.get("gasLimit");
        double payount = price * Integer.parseInt(size) * Integer.parseInt(useFulTime);
        if (Validate.isEmpty(walletPrivateKey) || Validate.isEmpty(size)) {
            throw new WebException(MySystemCode.DISK_EXCEPTION_CREATE_ERROR);
        }
        String toAddress = FileUtil.getAttr();
        //支付
        try {
            //ContractUtils.transferByUser(walletPrivateKey, toAddress, payount + "");
            TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payount + "", gasPrice, gasLimit);
        } catch (Exception e) {
            throw new WebException(MySystemCode.DISK_EXCEPTION_CREATE_ERROR);
        }

        // 组装转账数据
        String DealNote = "云" + (type.equals("disk") ? "磁盘" : "数据库") + "购买";
        //String gasFee = "0";
        WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
        walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
        walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
        walletDealDetailBO.setTransferToken(payount + "");
        walletDealDetailBO.setWalletIntoAddress(toAddress);
        walletDealDetailBO.setWalletOutAddress(walletAddress);
        walletDealDetailBO.setTransType("ZSL");
        // 将转账记录存入数据库
        int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
        if (createWalletDealDetailResultInt < 0) {
            throw new WebException(MySystemCode.DISK_EXCEPTION_CREATE_ERROR);
        }

        //创建磁盘
        //保存磁盘信息
        int time = Integer.parseInt(useFulTime);
        int diskSize = Integer.parseInt(size) * 1024;
        if (type.equals("disk"))
            cloudDisksService.addCloudDisk(diskName, time, diskSize, walletPublicKey);
        else
            cloudDisksService.addCloadDatabase(diskName, time, diskSize, walletPublicKey);
        resultMap.put("code", 0);
        resultMap.put("message", "success");
        return resultMap;
    }


    @RequestMapping(value = "/modifyCapacity.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "磁盘数据库扩容", position = 1)
    public Map modifyCloudCap(@RequestParam(value = "id", required = true) @ApiParam(value = "磁盘id", required = true) String id,
                              @RequestParam(value = "type", required = true) @ApiParam(value = "类型(disk-磁盘，db-数据库)", required = true) String type,
                              @RequestParam(value = "addSizeNum", required = true) @ApiParam(value = "新增大小", required = true) String addSizeNum,
                              @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "钱包地址", required = true) String walletAddress,
                              @RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
                              @RequestParam(value = "gasFee", required = true) @ApiParam(value = "矿工费", required = true) String gasFee) {
        Map<String, Object> result = new HashMap<>();


        Map<String, BigInteger> gasFeeMap = TransferUtils.covertZSLGasFee(gasFee);
        BigInteger gasPrice = gasFeeMap.get("gasPrice");
        BigInteger gasLimit = gasFeeMap.get("gasLimit");

        //支付
        String toAddress = FileUtil.getAttr();
        if (type.equals("disk")) {
            CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(Integer.parseInt(id));
            Date endDate = cloudDiskBO.getEndTime();
            Instant instant = endDate.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate endLocal = localDateTime.toLocalDate();
            Long length = LocalDate.now().until(endLocal, ChronoUnit.DAYS);
            double payount = price * Integer.parseInt(addSizeNum) * length;

            //支付
            try {
                //ContractUtils.transferByUser(walletPrivateKey, toAddress, payount + "");
                TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payount + "", gasPrice, gasLimit);
            } catch (Exception e) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_ADDROOM_ERROR);
            }

            // 组装转账数据
            String DealNote = "云" + (type.equals("disk") ? "磁盘" : "数据库") + "扩容";
            // String gasFee = "0";
            WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
            walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
            walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
            walletDealDetailBO.setTransferToken(payount + "");
            walletDealDetailBO.setWalletIntoAddress(toAddress);
            walletDealDetailBO.setWalletOutAddress(walletAddress);
            walletDealDetailBO.setTransType("ZSL");
            // 将转账记录存入数据库
            int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
            if (createWalletDealDetailResultInt < 0) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_ADDROOM_ERROR);
            }

            cloudDisksService.dilatationDiskCap(Integer.parseInt(id), Integer.parseInt(addSizeNum));
        } else {

            CloudDataBaseBO dataBaseBO = cloudDisksService.getCloudDatabaseBO(Integer.parseInt(id));
            Date endDate = dataBaseBO.getEndTime();
            Instant instant = endDate.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            LocalDate endLocal = localDateTime.toLocalDate();
            Long length = LocalDate.now().until(endLocal, ChronoUnit.DAYS);
            double payount = price * Integer.parseInt(addSizeNum) * length;
            try {
                //ContractUtils.transferByUser(walletPrivateKey, toAddress, payount + "");
                TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payount + "", gasPrice, gasLimit);
            } catch (Exception e) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_ADDROOM_ERROR);
            }

            // 组装转账数据
            String DealNote = "云" + (type.equals("disk") ? "磁盘" : "数据库") + "扩容";
            //String gasFee = "0";
            WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
            walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
            walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
            walletDealDetailBO.setTransferToken(payount + "");
            walletDealDetailBO.setWalletIntoAddress(toAddress);
            walletDealDetailBO.setWalletOutAddress(walletAddress);
            walletDealDetailBO.setTransType("ZSL");
            // 将转账记录存入数据库
            int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
            if (createWalletDealDetailResultInt < 0) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_ADDROOM_ERROR);
            }
            cloudDisksService.dilatationDatabaseCap(Integer.parseInt(id), Integer.parseInt(addSizeNum));
        }
        result.put("code", 0);
        result.put("message", "success");
        return result;
    }


    @RequestMapping(value = "/modifyUserFulTime.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "磁盘数据库续费", position = 1)
    public Map modifyUserFulTime(@RequestParam(value = "id", required = true) @ApiParam(value = "磁盘id", required = true) String id,
                                 @RequestParam(value = "type", required = true) @ApiParam(value = "类型(disk-磁盘，db-数据库)", required = true) String type,
                                 @RequestParam(value = "times", required = true) @ApiParam(value = "续费天数", required = true) String times,
                                 @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "钱包地址", required = true) String walletAddress,
                                 @RequestParam(value = "walletPrivateKey", required = true) @ApiParam(value = "私钥", required = true) String walletPrivateKey,
                                 @RequestParam(value = "gasFee", required = true) @ApiParam(value = "矿工费", required = true) String gasFee) {

        Map<String, Object> result = new HashMap<>();

        Map<String, BigInteger> gasFeeMap = TransferUtils.covertZSLGasFee(gasFee);
        BigInteger gasPrice = gasFeeMap.get("gasPrice");
        BigInteger gasLimit = gasFeeMap.get("gasLimit");

        //支付
        String toAddress = FileUtil.getAttr();
        if (type.equals("disk")) {
            CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(Integer.parseInt(id));
            double size = cloudDiskBO.getCapacity() / 1024;
            double payCount = price * Integer.parseInt(times) * size;
            try {
                //ContractUtils.transferByUser(walletPrivateKey, toAddress, payount + "");
                TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payCount + "", gasPrice, gasLimit);
            } catch (Exception e) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_RENEWAl_ERROR);
            }

            // 组装转账数据
            String DealNote = "云" + (type.equals("disk") ? "磁盘" : "数据库") + "扩容";
            //String gasFee = "0";
            WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
            walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
            walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
            walletDealDetailBO.setTransferToken(payCount + "");
            walletDealDetailBO.setWalletIntoAddress(toAddress);
            walletDealDetailBO.setWalletOutAddress(walletAddress);
            walletDealDetailBO.setTransType("ZSL");
            // 将转账记录存入数据库
            int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
            if (createWalletDealDetailResultInt < 0) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_RENEWAl_ERROR);
            }
            cloudDisksService.dilatationUseFulTime(Integer.parseInt(times), Integer.parseInt(id));
        } else {
            CloudDataBaseBO dataBaseBO = cloudDisksService.getCloudDatabaseBO(Integer.parseInt(id));
            double size = dataBaseBO.getCapacity() / 1024;
            double payCount = price * Integer.parseInt(times) * size;
            try {
                //ContractUtils.transferByUser(walletPrivateKey, toAddress, payount + "");
                TransferUtils.transferZSLFroGasFee(walletPrivateKey, toAddress, payCount + "", gasPrice, gasLimit);
            } catch (Exception e) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_RENEWAl_ERROR);
            }

            // 组装转账数据
            String DealNote = "云" + (type.equals("disk") ? "磁盘" : "数据库") + "扩容";
            //String gasFee = "0";
            WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
            walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
            walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
            walletDealDetailBO.setTransferToken(payCount + "");
            walletDealDetailBO.setWalletIntoAddress(toAddress);
            walletDealDetailBO.setWalletOutAddress(walletAddress);
            walletDealDetailBO.setTransType("ZSL");
            // 将转账记录存入数据库
            int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
            if (createWalletDealDetailResultInt < 0) {
                throw new WebException(MySystemCode.DISK_EXCEPTION_RENEWAl_ERROR);
            }
            cloudDisksService.dilatationDatabaseUseFulTime(Integer.parseInt(times), Integer.parseInt(id));
        }
        result.put("code", 0);
        result.put("message", "success");
        return result;
    }


    @RequestMapping(value = "/getCloudDiskList.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查看磁盘", position = 1)
    public Map getCloudDiskList(
            @RequestParam(value = "walletPublicKey", required = true) @ApiParam(value = "钱包公钥", required = true) String walletPublicKey) {

        if (Validate.isEmpty(walletPublicKey))
            throw new WebException(MySystemCode.BIZ_PARAMERRO_EXCEPTION);
        Map<String, Object> resultMap = new HashMap<>();

        List<CloudDiskBO> cloudDiskBOS = cloudDisksService.getCloudDiskBO(walletPublicKey);
        for (CloudDiskBO cloudDiskBO : cloudDiskBOS) {
            double cap = cloudDiskBO.getCapacity();
            cloudDiskBO.setCapacity(cap / 1024);
        }
        resultMap.put("disk", cloudDiskBOS);
        List<CloudDataBaseBO> cloudDataBaseBOS = cloudDisksService.getCloudDatabases(walletPublicKey);
        for (CloudDataBaseBO dataBaseBO : cloudDataBaseBOS) {
            double cap = dataBaseBO.getCapacity();
            dataBaseBO.setCapacity(cap / 1024);
        }
        resultMap.put("databases", cloudDataBaseBOS);

        // 每次调用此接口，可以进行一次删除操作，删除过期记录，及关联的数据
        cloudDisksService.deleteDiskByTime();
        cloudDisksService.deleteDBByTime();

        return resultMap;
    }

    @RequestMapping(value = "/getCloudDiskInfo.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查看", position = 1)
    public Map getCloudDiskInfo(@RequestParam(value = "diskId", required = true) @ApiParam(value = "磁盘id", required = true) String diskId,
                                @RequestParam(value = "page", required = true) @ApiParam(value = "页码", required = true) String page,
                                @RequestParam(value = "size", required = true) @ApiParam(value = "每页显示大小", required = true) String size) {
        CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(Integer.parseInt(diskId));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("cloudId", cloudDiskBO.getCloudId());
        resultMap.put("diskName", cloudDiskBO.getDiskName());
        resultMap.put("capacity", cloudDiskBO.getCapacity() / 1024);
        resultMap.put("startTime", cloudDiskBO.getStartTime().getTime());
        resultMap.put("endTime", cloudDiskBO.getEndTime().getTime());
        //下载数据
        int count = cloudFileService.getCloudFileCount(cloudDiskBO.getCloudId());
        int startRows = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
        List<CloudFileBO> datas = cloudFileService.getCloudFiles(cloudDiskBO.getCloudId() + "", startRows, Integer.parseInt(size));
        resultMap.put("data", datas);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "/getFile.json", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "文件下载", position = 2)
    public void getFileDownLoad(@RequestParam(value = "fileIds", required = true) @ApiParam(value = "文件id(批量用，分割)", required = true) String fileIds,
                                HttpServletResponse response) throws IOException {
        String[] ids = fileIds.split(",");
        List<CloudFileBO> cloudFileBOS = cloudFileService.getCloudFiles(ids);
        if (cloudFileBOS.size() > 1) {
            List<File> files = new ArrayList<>();
            int id = 0;
            for (CloudFileBO cloudFileBO : cloudFileBOS) {
                id = cloudFileBO.getCloudId();
                File file = new File(cloudFileBO.getPath());
                if (file.exists())
                    files.add(file);
            }
            CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(id);
            File zipTemp = File.createTempFile(cloudDiskBO.getDiskName(), ".zip");
            FileUtil.getZipFile(files, zipTemp);
            InputStream fis = new BufferedInputStream(new FileInputStream(zipTemp));
            String fileName = zipTemp.getName();
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.addHeader("Content-Length", "" + zipTemp.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
            zipTemp.delete();
        } else if (cloudFileBOS.size() == 1){
            CloudFileBO cloudFileBO = cloudFileBOS.get(0);
            String filePath = cloudFileBO.getPath();
            File file = new File(filePath);
            //File file = new File("D:\\SegmentFault 思否-采集3902761206290872608.xlsx");
            if (!file.exists())
                throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_FILE_ERROR);
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            String fileName = file.getName().split("_")[0] ; //+ file.getName().substring(file.getName().lastIndexOf("."))
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        }else {
            throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_FILE_ERROR);
        }
    }


    @RequestMapping(value = "/getFileDataInDisk.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取文件里面的数据")
    public Map getFileData(@RequestParam(value = "fileId", required = true) @ApiParam(value = "文件id", required = true) String fileId,
                           @RequestParam(value = "page", required = true) @ApiParam(value = "页码", required = true) String page,
                           @RequestParam(value = "size", required = true) @ApiParam(value = "大小", required = true) String size)
            throws Exception {
        int startRow = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
        //只能一条数据
        Map<String, Object> resultMap = new HashMap<>();
        List<CloudFileBO> cloudFileBOS = cloudFileService.getCloudFiles(fileId.split(","));
        if (cloudFileBOS.size() > 0) {
            CloudFileBO cloudFileBO = cloudFileBOS.get(0);
            String filepath = cloudFileBO.getPath();
            File file = new File(filepath);
            CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(cloudFileBO.getCloudId());
            if (file.exists())
                throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_FILE_ERROR);
            int count = ExcelUtil.readExcelRowNum(filepath);
            resultMap.put("startTime", cloudDiskBO.getStartTime().getTime());
            resultMap.put("endTime", cloudDiskBO.getEndTime().getTime());
            resultMap.put("count", count);

            Map data = new HashMap();
            List<String> titles = ExcelUtil.readExcelFirstRow(filepath);
            data.put("title", titles);

            List<String[]> dataList = ExcelUtil.readExcel(filepath, startRow, Integer.parseInt(size));
            data.put("data", dataList);

            resultMap.put("data", data);
        }
        return resultMap;
    }


    @RequestMapping(value = "/getCloudDatabaseDataInfo.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查看数据库", position = 1)
    public Map getCloudDatabaseDataInfo(
            @RequestParam(value = "databaseId", required = true) @ApiParam(value = "databaseId", required = true) String databaseId,
            @RequestParam(value = "page", required = true) @ApiParam(value = "页码", required = true) String page,
            @RequestParam(value = "size", required = true) @ApiParam(value = "大小", required = true) String size,
            @RequestParam(value = "tableName", required = true) @ApiParam(value = "表名", required = true) String tableName) {

        CloudDataBaseBO dataBaseBO = cloudDisksService.getCloudDatabaseBO(Integer.parseInt(databaseId));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("databaseId", dataBaseBO.getDataBaseID());
        resultMap.put("name", dataBaseBO.getName());
        resultMap.put("capacity", dataBaseBO.getCapacity() / 1024);
        resultMap.put("startTime", dataBaseBO.getStartTime().getTime());
        resultMap.put("endTime", dataBaseBO.getEndTime().getTime());
        resultMap.put("databasePwd", dataBaseBO.getDatabasePwd());
        String tablename = "crawl_data";
        //下载数据
        if (!Validate.isEmpty(tableName) && tableName.equals("news_data_2")) {
            tablename = "crawl_data_news";
        }
        int count = cloudFileService.getCrawlDataCount(tablename, dataBaseBO.getDataBaseID());
        int startRows = (Integer.parseInt(page) - 1) * Integer.parseInt(size);
        List<Object> data = new ArrayList<>();
        List<CrawlDataBO> datas = cloudFileService.getCrawlDataList(tablename, Integer.parseInt(databaseId), startRows, Integer.parseInt(size));
        List<String> titles = Arrays.asList(new String[]{"id", "title", "content", "img", "url", "author", "publishTime"});
        List<Object> dadtaList = null;
        for (CrawlDataBO crawlDataBO : datas) {
            JSONObject jsonObject = JSONObject.fromObject("{}");
            jsonObject.put("id", crawlDataBO.getId());
            jsonObject.put("title", crawlDataBO.getTitle());
            jsonObject.put("content", crawlDataBO.getContent());
            jsonObject.put("img", crawlDataBO.getImg());
            jsonObject.put("url", crawlDataBO.getUrl());
            jsonObject.put("author", crawlDataBO.getAuthor());
            jsonObject.put("logo", crawlDataBO.getLogo());
            jsonObject.put("readNum", crawlDataBO.getReadNum());
            jsonObject.put("publishTime", crawlDataBO.getPublishTime());
            data.add(jsonObject);
        }
        resultMap.put("data", data);
        resultMap.put("title", titles);
        resultMap.put("count", count);

        return resultMap;
    }

    @RequestMapping(value = "/updateDatabasePwd.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查看数据库", position = 1)
    public Map updatepwd(@RequestParam(value = "databaseId", required = true) @ApiParam(value = "databaseId", required = true) String databaseId,
                         @RequestParam(value = "oldPwd", required = true) @ApiParam(value = "旧密码", required = true) String oldPwd,
                         @RequestParam(value = "pwd", required = true) @ApiParam(value = "密码", required = true) String pwd) {
        CloudDataBaseBO dataBaseBO = cloudDisksService.getCloudDatabaseBO(Integer.parseInt(databaseId));
        String pwdIN = dataBaseBO.getDatabasePwd();
        if (pwdIN.equals(oldPwd)) {
            dataBaseBO.setDatabasePwd(pwd);
            cloudDisksService.updateDatabasePWD(dataBaseBO.getDataBaseID(), pwd);
        } else {
            throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_PWD_ERROR);
        }
        return new HashMap();
    }

    @RequestMapping(value = "/downloadData.json", method = RequestMethod.GET)
    @ResponseBody
    public void downloadDataIndatabase(@RequestParam(value = "databaseId", required = true) @ApiParam(value = "databaseId", required = true) String databaseId,
                                       @RequestParam(value = "tableName", required = true) @ApiParam(value = "表名", required = true) String tableName
    ) {
        String tablename = "crawl_data";
        //下载数据
        if (!Validate.isEmpty(tableName) && tableName.equals("news_data_2")) {
            tablename = "crawl_data_news";
        }
        int count = cloudFileService.getCrawlDataCount(tablename, Integer.parseInt(databaseId));
        double size = 50;
        double pageSize = Math.ceil(count / size);
        List<String> titles = new ArrayList<>();
        titles.add("title");
        titles.add("content");
        titles.add("url");
        titles.add("author");
        SXSSFWorkbook workbook = ExcelUtil.createExcel(titles);
        Sheet sh = workbook.getSheetAt(0);
        int startRows = 0;
        while (pageSize > 0) {
            List<CrawlDataBO> datas = cloudFileService.getCrawlDataList(tablename, Integer.parseInt(databaseId), startRows, 50);
            for (int j = startRows; j < datas.size(); j++) {
                CrawlDataBO crawlDataBO = datas.get(j);
                Row row = sh.createRow(j + 1);
                row.createCell(0).setCellValue(crawlDataBO.getTitle());
                row.createCell(1).setCellValue(crawlDataBO.getContent());
                row.createCell(2).setCellValue(crawlDataBO.getUrl());
                row.createCell(3).setCellValue(crawlDataBO.getAuthor());
            }
        }
        //TODO 文件下载输出
    }


    @RequestMapping(value = "/getTableName.json", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取表名")
    public Map getTableNames() {
        Map<String, Object> tablesname = new HashMap<>();
        String[] tableNames = new String[]{"news_data_1", "news_data_2"};
        tablesname.put("tableName", tableNames);
        return tablesname;
    }

    @RequestMapping(value = "/getSYSTEMTIME.json", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取系统时间")
    public Map getSYSTEMTIME() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("SYSTEMTIME", new Date().getTime());
        return resultMap;
    }


}

