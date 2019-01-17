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
public class DappControllerTest extends AbstractSpringBaseControllerTest {


    @Test
    @Rollback(value = false)
    public void deleteDappByIdTest() throws Exception {
        String requestURI = "/dapp/deleteDappById.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("dappId","1")
                        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getDataBaseListByWalletAddressTest() throws Exception {
        String requestURI = "/dapp/getDataBaseListByWalletAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","92edf45293e73695ac0c721b1acb5bf44626d83f416695c315189366b9d4947f3426d68f1b0abfe0fc52913154f969712e21f9232d26587b6b26e9e7a30b5ecc")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void deleteDappRelevantByDappIdTest() throws Exception {
        String requestURI = "/dapp/deleteDappRelevantByDappId.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("dappId","1").param("dataBaseTableId","1")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void updateRelevantDBStatusTest() throws Exception {
        String requestURI = "/dapp/dappRelevantDB.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("dappId","550")
                        .param("dataBaseId","2")
                        .param("walletAddress","92edf45293e73695ac0c721b1acb5bf44626d83f416695c315189366b9d4947f3426d68f1b0abfe0fc52913154f969712e21f9232d26587b6b26e9e7a30b5ecc")
                        .param("dataBaseName","自行车")
                        .param("dataBaseTableName","crawl_data_news")
                        .param("dataBaseToken","V1ZH90")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void dappRelevantDBTest() throws Exception {
        String requestURI = "/dapp/updateRelevantDBStatus.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("dataBaseTableId","1")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getDappByMacAddressTest() throws Exception {
        String requestURI = "/dapp/getDappByMacAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("macAddress","0A-00-27-00-00-0E")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void createDapp() throws Exception {
        String requestURI = "/dapp/createDapp.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("dappName","号币").param("dappIntro","这个是关于号币的")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getDappDetailById() throws Exception {
        String requestURI = "/dapp/getDappDetailByDappId.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("dappId","1")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getDataToDappTest() throws Exception {
        String requestURI = "/dapp/getDataToDapp.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("dataBaseTableName","news_data_1")
                        .param("dataBaseName","自行车2")
                        .param("type","zhence")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

}
