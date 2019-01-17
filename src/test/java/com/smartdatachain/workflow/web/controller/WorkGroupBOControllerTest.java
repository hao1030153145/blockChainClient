package com.smartdatachain.workflow.web.controller;

import com.jeeframework.testframework.AbstractSpringBaseControllerTest;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 项目单元测试类
 */
public class WorkGroupBOControllerTest extends AbstractSpringBaseControllerTest {


    @Test
    @Rollback(value = false)
    public void getWorkGroupTest() throws Exception {
        String requestURI = "/workGroup/getWorkGroup.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("workGroupName","")
                        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void addWorkGroupTest() throws Exception {
        String requestURI = "/workGroup/addWorkGroup.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("name","测试组2")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void deleteWorkGroupByIdTest() throws Exception {
        String requestURI = "/workGroup/deleteWorkGroupById.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("id","4").param("isDelete","1")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void updateWorkGroupByIdTest() throws Exception {
        String requestURI = "/workGroup/updateWorkGroupById.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("id","2").param("name","汽车之家组")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getWorkGroupDetailByParamTest() throws Exception {
        String requestURI = "/workGroup/getWorkGroupDetailByParam.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("collectionId","553")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void updateWorkGroupDetailByIdTest() throws Exception {
        String requestURI = "/workGroup/updateWorkGroupDetailById.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("appId","572").param("collectionId","571")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void deleteWorkGroupDetailByIdTest() throws Exception {
        String requestURI = "/workGroup/deleteWorkGroupDetailById.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("appId","2").param("collectionId","1")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void saveWorkGroupDetailTest() throws Exception {
        String requestURI = "/workGroup/saveWorkGroupDetail.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("name","测试采集任务")
                        .param("type","crawler")
                        .param("info","123info")
                        .param("collectionId","1")
                        .param("status","0")
                        .param("typeSub","auto")
                        .param("rule","123rule")
                        .param("graph","{\"attr\":{\"listSelector\":\"//div[contains(@class,'main-container')]/div[contains(@class,'con-l')]/div[contains(@class,'search-list-con')]//dl[contains(@class,'search-list')]\",\"extractFields\":{\"标题\":{\"extractType\":\"text\",\"notNull\":false},\"标题链接\":{\"extractType\":\"href\",\"notNull\":false},\"search-detail\":{\"extractType\":\"text\",\"notNull\":false},\"作者：\":{\"extractType\":\"text\",\"notNull\":false},\"字段0\":{\"extractType\":\"text\",\"notNull\":false}},\"settings\":{\"loadImages\":true,\"loadAds\":true,\"userAgent\":false,\"reborn\":{\"cookie\":0,\"userAgent\":0},\"basePath\":\"C:\\\\Users\\\\Administrator\\\\AppData\\\\Local\\\\Houyi\\\\images\\\\2447737\",\"proxy\":false,\"timeoutAjax\":1000,\"downloadImage\":false,\"cron\":{\"frequency\":\"once\",\"once_date_start_which\":\"1\",\"once_date_start\":\"2018-10-23\",\"date_between_which\":\"1\",\"date_between\":\"2018-10-23 - 2018-10-23\",\"time_start_which\":\"1\",\"time_start\":\"16:8\",\"time_end_which\":\"1\",\"time_end\":\"00:00\",\"realtime_interval\":\"600\"},\"startCron\":false,\"publishAutomatic\":false,\"savedConfigName\":\"\"},\"session_id\":\"1540278376231\",\"urls\":[\"https://so.csdn.net/so/search/s.do?q=thinkjs&t=&o=&s=&l=\"]},\"script\":[],\"pageType\":\"list\",\"pager\":{\"action\":\"\",\"type\":\"no-next\",\"clickedValue\":\"\",\"selector\":\"/html/body/div[@class='main-container']/div[@class='con-l']/div[@class='csdn-pagination hide-set']/span[@class='page-nav']/a[@class='btn btn-xs btn-default btn-next']\",\"pageNum\":\"-1\"},\"fields\":[{\"name\":\"标题\",\"selector\":\"//dd[contains(@class,'search-link')]/a\",\"extractType\":\"text\",\"notNull\":false,\"inList\":true},{\"name\":\"标题链接\",\"selector\":\"//dd[contains(@class,'search-link')]/a\",\"extractType\":\"href\",\"notNull\":false,\"inList\":true},{\"name\":\"search-detail\",\"selector\":\"//dd[contains(@class,'search-detail')]/em[1]\",\"extractType\":\"text\",\"notNull\":false,\"inList\":true},{\"name\":\"作者：\",\"selector\":\"//dd[contains(@class,'author-time')]/a[1]\",\"extractType\":\"text\",\"notNull\":false,\"inList\":true},{\"name\":\"字段0\",\"selector\":\"//dt[1]/a[1]\",\"extractType\":\"text\",\"notNull\":false,\"inList\":true}]}")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void getCloudDiskListTest() throws Exception {
        String requestURI = "/cloud/getCloudDiskList.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("walletPublicKey","083ae90cda3d9f3fac833fd838844ac1604a51bb")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void getFileTest() throws Exception {
        String requestURI = "/cloud/getFile.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI)
                        .param("fileIds","5")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

}
