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
 * author：haolen
 */
public class CloudTest extends AbstractSpringBaseControllerTest {


    @Test
    @Rollback(value = false)
    public void deleteDappByIdTest() throws Exception {
        String requestURI = "/cloudData/addData2Database.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI)
                        .param("data","{\n" +
                                "\t\"title\": [\"title\", \"标题链接\", \"author\", \"img\", \"read_num\", \"字段2\", \"publish_time\", \"source\", \"content\"],\n" +
                                "\t\"data\": [\n" +
                                "\t\t[\"比特大陆回应拖欠台积电3亿美元债务传闻：纯属谣言 一切以财报为准\", \"https://www.jinse.com/news/bitcoin/267130.html\", \"严茹 \", \"https://img.jinse.com/1293118_small.png\", \"109897\", \"台积电的芯片也会放缓上市，因为他们不再会匆忙开始工作，除非他们收到未来订单的全额付款。\", \"18小时前\", \"金色财经_在这里，读懂区块链\", \"<p>后续事件发展金色财经会持续报道。</p>\"]\n" +
                                "\t]\n" +
                                "}")
                        .param("tableName","crawl_data")
                        .param("dataType","toutiao")
                        .param("databaseId","1")
                        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0);
    }



}
