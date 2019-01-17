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
public class WalletControllerTest extends AbstractSpringBaseControllerTest {


    @Test
    @Rollback(value = false)
    public void getWorkGroupTest() throws Exception {
        String requestURI = "/wallet/createWallet.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletName","皓月钱包").param("walletPassword","hao19951230")
                        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getAddressAndMnemonicByPrivateKeyTest() throws Exception {
        String requestURI = "/wallet/getAddressAndMnemonicByPrivateKey.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("privateKey","adef5263e4378e256fc0909e852f3f2f8c22262cc043dfec4643e3f150bff427")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void updateWalletPasswordByAddressTest() throws Exception {
        String requestURI = "/wallet/updateWalletPasswordByAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","70ff9d0439af9c6e0bcedc339a43f5a09aeb2bb3").param("walletPassword","Hao19951230")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getMnemonicByNameAndAddressTest() throws Exception {
        String requestURI = "/wallet/deleteWalletByAddressAndPwd.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","1cab420318e54640cfd730b485ec535a56ef17be").param("walletPassword","Hao19951230")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void getMnemonicByPwdAndAddressTest() throws Exception {
        String requestURI = "/wallet/getMnemonicByPwdAndAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","70ff9d0439af9c6e0bcedc339a43f5a09aeb2bb3").param("walletPassword","Hao19951230")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void getWalletKeyByPwdAndAddressTest() throws Exception {
        String requestURI = "/wallet/getWalletKeyByPwdAndAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","70ff9d0439af9c6e0bcedc339a43f5a09aeb2bb3").param("walletPassword","Hao19951230")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getWalletDealDetailByAddressTest() throws Exception {
        String requestURI = "/wallet/getWalletDealDetailByAddress.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","0x02407e6d4b9dbacf22eb23c6465045e841864e50").param("transType","ZSL")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void tranferTokenTest() throws Exception {
        String requestURI = "/wallet/tranferToken.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
//                        .param("walletAddress","0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb")
//                        .param("walletPrivateKey","6DFCCE26F2BDAD72295931B26670DD77725C964DBF2097BDB8C150473FFECA47")
//                        .param("transferAmount","200")
//                        .param("toAddress","0x0E7cEB98CE5015D6EAE8517585576Eb7177FAe6f")
//                        .param("gasFee","0.1")
//                        .param("DealNote","这个是测试的交易备注")
                        .param("walletAddress","0x0E7cEB98CE5015D6EAE8517585576Eb7177FAe6f")
                        .param("walletPrivateKey","61ec076d5464b8aa69a240802d4419a5164d5c3bdc3a29037a6a3a6ff7417626")
                        .param("transferAmount","5000")
                        .param("toAddress","0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb")
                        .param("gasFee","0.000048")
                        .param("DealNote","这个是测试的交易时间")
                        .param("transType","ZSL")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

      @Test
    @Rollback(value = false)
    public void getBalanceTest() throws Exception {
        String requestURI = "/wallet/getBalance.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("walletAddress","0x083ae90cda3d9f3fac833fd838844ac1604a51bb")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


    @Test
    @Rollback(value = false)
    public void getCloudDatabaseDataInfoTest() throws Exception {
        String requestURI = "/cloud/getCloudDatabaseDataInfo.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("databaseId","6")
                        .param("page","1")
                        .param("size","1")
                        .param("tableName","news_data_1")

        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void addDataTest() throws Exception {
        String requestURI = "/cloudData/addData.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("data","{\n" +
                                "\t\"title\": [\"title\", \"标题链接\", \"author\", \"img\", \"read_num\", \"字段2\", \"publish_time\", \"source\", \"content\"],\n" +
                                "\t\"data\": [\n" +
                                "\t\t[\"朱幼平：《香港证监会虚拟货币监管规定》有“破冰”的示范效应\", \"https://www.jinse.com/bitcoin/267332.html\", \"区块小姐姐 \", \"https://img.jinse.com/1294174_small.png\", \"30207\", \"2018年11月1日香港证监会公布的《香港证监会虚拟货币监管规定》有“破冰”的示范效应，推动1CO和加密币监管的规范性。\", \" 1小时前\", \"金色财经_在这里，读懂区块链\", \"不会合法。大概率内地政策不会跟随香港比较自由的监管模式。</p>\"]\n" +
                                "\t]\n" +
                                "}")
                        .param("mark","aaa")
                        .param("walletAddress","0x857190c2763aBc7673F88Cbd8d9DFdB0Fe014aFb")
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }

    @Test
    @Rollback(value = false)
    public void getGasFeeTest() throws Exception {
        String requestURI = "/wallet/getGasFee.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }


}
