package com.smartdatachain.api.web.controller;

import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.WebException;
import com.smartdatachain.api.biz.service.CloudDisksService;
import com.smartdatachain.api.biz.service.CloudFileService;
import com.smartdatachain.api.biz.service.WalletService;
import com.smartdatachain.api.integration.bo.*;
import com.smartdatachain.api.util.DataTempFactory;
import com.smartdatachain.api.util.ExcelUtil;
import com.smartdatachain.api.util.FileUtil;
import com.smartdatachain.api.util.eth.contract.ContractUtils;
import com.smartdatachain.api.util.eth.contract.TransferUtils;
import com.smartdatachain.api.web.exception.MySystemCode;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包: com.smartdatachain.api.web.controller
 * 源文件:CloudDataController.java
 * 云端数据
 *
 * @author Allen ,Inc. All rights reserved.2018年10月27日
 */
@Controller(value = "cloudDataController")
@RequestMapping(value = "/cloudData")
@Api(value = "云端数据")
public class CloudDataController {


    @Resource
    private CloudFileService cloudFileService;
    @Resource
    private CloudDisksService cloudDisksService;
    @Resource
    private WalletService walletService;

    @RequestMapping(value = "/addCloudData.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加文件到数据库")
    public Map addDataInCloud(@RequestParam(value = "data", required = true) @ApiParam(value = "数据JSON {title:[],data:[[],[]]}", required = true) String data,
                              @RequestParam(value = "fileName", required = true) @ApiParam(value = "规则名称", required = true) String fileName,
                              @RequestParam(value = "walletId", required = true) @ApiParam(value = "钱包id", required = true) String walletId,
                              @RequestParam(value = "diskId", required = true) @ApiParam(value = "磁盘id", required = true) String diskId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            CloudDiskBO cloudDiskBO = cloudDisksService.getCloudDisk(Integer.parseInt(diskId));
            if (cloudDiskBO == null)
                throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_ERROR);
            File file = File.createTempFile(fileName, ".xlsx");
            JSONObject dataJSON = JSONObject.fromObject(data);
            JSONArray array = dataJSON.getJSONArray("title");
            List<String> titles = JSONArray.toList(array, new String(), new JsonConfig());
            SXSSFWorkbook sxssfWorkbook = ExcelUtil.createExcel(titles);

            JSONArray dataArray = dataJSON.getJSONArray("data");
            Sheet sheet = sxssfWorkbook.getSheetAt(0);
            int row = 1;
            for (Object dataArays : dataArray) {
                JSONArray datas = JSONArray.fromObject(dataArays);
                Row workRow = sheet.createRow(row);
                for (int i = 0; i < datas.size(); i++) {
                    String value = datas.getString(i);
                    Cell cell = workRow.createCell(i);
                    cell.setCellValue(value);
                }
                row++;
            }

            ((SXSSFSheet) sheet).flushRows();
            FileOutputStream fos = new FileOutputStream(file);
            fos.flush();
            sxssfWorkbook.write(fos);
            fos.close();

            double usedCap = cloudDiskBO.getUsedCap();
            usedCap = usedCap + file.length();

            if (usedCap > cloudDiskBO.getCapacity() * 1024) {
                file.delete();
                throw new WebException(
                        MySystemCode.RELEVENT_EXCEPTION_CLOUD_NO_ERROR);
            }
            String filePath = file.getPath();
            CloudFileBO cloudFileBO = new CloudFileBO();
            cloudFileBO.setCloudId(Integer.parseInt(diskId));
            cloudFileBO.setPath(filePath);
            cloudFileBO.setName(fileName);
            cloudFileBO.setFileSize(file.length());
            cloudFileBO.setWalletId(walletId);
            cloudFileService.addCloudFile(cloudFileBO);

            //更新磁盘空间

            cloudDisksService.updateCloudFile(cloudDiskBO.getCloudId(), usedCap);


            resultMap.put("code", 0);
            resultMap.put("message", "success");
        } catch (Exception e) {
            resultMap.put("code", -1);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping(value = "/addData2Database.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推送数据到数据库")
    public Map addData2Database(@RequestParam(value = "data", required = true) @ApiParam(value = "数据JSON {title:[],data:[[],[]]}", required = true) String data,
                                @RequestParam(value = "tableName", required = true) @ApiParam(value = "表名称", required = true) String tableName,
                                @RequestParam(value = "dataType", required = true) @ApiParam(value = "数据类型", required = true) String dataType,
                                @RequestParam(value = "databaseId", required = true) @ApiParam(value = "数据库id", required = true) String databaseId) {

        CloudDataBaseBO cloudDataBaseBO = cloudDisksService.getCloudDatabaseBO(Integer.parseInt(databaseId));
        if (cloudDataBaseBO == null)
            throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_NO_ERROR);

        if (cloudDataBaseBO.getUsedCap() >= cloudDataBaseBO.getCapacity() * 1024)
            throw new WebException(MySystemCode.RELEVENT_EXCEPTION_CLOUD_NO_ERROR);

        String tName = "crawl_data";
        if (!Validate.isEmpty(tableName) && tableName.equalsIgnoreCase(""))
            tName = "crawl_data_news";
        JSONObject dataJSON = JSONObject.fromObject(data);
        JSONArray titles = dataJSON.getJSONArray("title");
        JSONArray jsonArray = dataJSON.getJSONArray("data");

        CrawlDataBO crawlDataBO = null;
        double size = cloudDataBaseBO.getUsedCap();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray datas = jsonArray.getJSONArray(i);
            if (datas.size() > 0) {
                crawlDataBO = new CrawlDataBO();
                for (int j = 0; j < datas.size(); j++) {
                    String titleName = titles.getString(j);
                    String value = datas.getString(j);
                    size += value.getBytes().length;
                    if (titleName.equalsIgnoreCase("title"))
                        crawlDataBO.setTitle(value);
                    else if (titleName.equalsIgnoreCase("content"))
                        crawlDataBO.setContent(value);
                    else if (titleName.equalsIgnoreCase("url"))
                        crawlDataBO.setUrl(value);
                    else if (titleName.equalsIgnoreCase("author"))
                        crawlDataBO.setAuthor(value);
                    else if (titleName.equalsIgnoreCase("source"))
                        crawlDataBO.setSource(value);
                    else if (titleName.equalsIgnoreCase("img"))
                        crawlDataBO.setImg(value);
                    else if (titleName.equalsIgnoreCase("publish_Time"))
                        crawlDataBO.setPublishTime(value);
                    else if (titleName.equalsIgnoreCase("logo"))
                        crawlDataBO.setLogo(value);
                    else if (titleName.equalsIgnoreCase("readNum"))
                        crawlDataBO.setReadNum(value);
                }
                crawlDataBO.setType(dataType);
                crawlDataBO.setDatabaseId(cloudDataBaseBO.getDataBaseID());
                cloudFileService.addCrawlData(tName, crawlDataBO);
            }
        }
        cloudDisksService.updateCloudDatabase(cloudDataBaseBO.getDataBaseID(), size);
        Map<String, Object> result = new HashMap<>();

        return result;
    }


    @RequestMapping(value = "/addData.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推送数据")
    public Map submitDataString(
            @RequestParam(value = "data", required = true) @ApiParam(value = "数据JSON {title:[],data:[[],[]]}", required = true) String data,
            @RequestParam(value = "mark", required = true) @ApiParam(value = "任务发布者标示", required = true) String mark,
            @RequestParam(value = "walletAddress", required = true) @ApiParam(value = "地址", required = true) String walletAddress) {
        System.out.println("推送数据》》》》数据json：" + data);
        Map<String, Object> result = new HashMap<>();
        DataTempFactory factory = DataTempFactory.getInstice();
        factory.add(mark, data);
        //支付
        String toAddress = FileUtil.getAttr();

        String payCount = ContractUtils.PAY_COUNT;
        String gasFee = ContractUtils.GAS_FEE;
        String privateKey = ContractUtils.OWNER_PRIVATE_KEY;
        //String payNum = FileUtil.getRandomNum(6);
        System.out.println("获得发布者的支付费用：" + payCount + "\n获得发布者给的手续费：" + gasFee + "\n获得中心者的私钥：" + privateKey);

        // 下面是将 任务奖励和挖矿奖励统一 组装，然后转给挖矿者
        //BigDecimal bigDecimal1 = new BigDecimal(payNum);
        //BigDecimal bigDecimal2 = new BigDecimal(payCount);
        //BigDecimal bigDecimal3 = bigDecimal1.add(bigDecimal2);

        Map<String, BigInteger> gasFeeMap = TransferUtils.covertZSLGasFee(gasFee);
        BigInteger gasPrice = gasFeeMap.get("gasPrice");
        BigDecimal gasPriceBig = new BigDecimal(gasPrice);
        BigDecimal gasD = new BigDecimal(1.4);
        BigInteger gasLimit = gasFeeMap.get("gasLimit");
        System.out.println("获得gasPrice的值：" + gasPrice + "\n获得gasLimit的值：" + gasLimit);
        try {
            //ContractUtils.reward(walletAddress, payNum); // 进行任务奖励转账
            //TransferUtils.transferZSLFroGasFee(privateKey, walletAddress, payCount, gasPrice, gasLimit);// 将中心者的钱转给任务完成者
            TransferUtils.transferZSLFroGasFee(privateKey, walletAddress, payCount, gasPriceBig.multiply(gasD).toBigInteger(), gasLimit);// 统一将中心者的钱转给任务完成者
        } catch (Exception e) {
            System.out.println("推送数据失败****************************");
            e.printStackTrace();
            //throw new WebException(MySystemCode.DATA_EXCEPTION_PUTDATA_ERROR);
        }

        // 组装转账数据
        String DealNote = "挖矿奖励";
        //String gasFee = "挖矿费";
        WalletDealDetailBO walletDealDetailBO = new WalletDealDetailBO();
        walletDealDetailBO.setDealNote(DealNote == null ? "" : DealNote);
        walletDealDetailBO.setGasFee(gasFee == null ? "0" : gasFee);
        walletDealDetailBO.setTransferToken(payCount);
        walletDealDetailBO.setWalletIntoAddress(walletAddress);
        walletDealDetailBO.setWalletOutAddress(toAddress);
        walletDealDetailBO.setTransType("ZSL");
        result.put("money", payCount);
        //result.put("gasFee", payCount);
        // 将转账记录存入数据库
        int createWalletDealDetailResultInt = walletService.createWalletDealDetail(walletDealDetailBO);
        if (createWalletDealDetailResultInt < 0) {
            throw new WebException(MySystemCode.DATA_EXCEPTION_PUTDATA_ERROR);
        }
        return result;
    }


    @RequestMapping(value = "/getData.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "推送数据")
    public Map getTempData(@RequestParam(value = "mark", required = true) @ApiParam(value = "任务发布者标示", required = true) String mark) {

        System.out.println("推送数据》》》任务发布者标示：" + mark);
        Map<String, Object> result = new HashMap<>();
        DataTempFactory factory = DataTempFactory.getInstice();
        String data = factory.get(mark);
        result.put("data", data);
        return result;
    }

}
