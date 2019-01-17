package com.smartdatachain.workflow.web.controller;

import com.jeeframework.testframework.AbstractSpringBaseControllerTest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 包: com.smartdatachain.workflow.web.controller
 * 源文件:TaskControllerTest.java
 *
 * @author Allen ,Inc. All rights reserved.2018年10月31日
 */
public class TaskControllerTest extends AbstractSpringBaseControllerTest
{
    @Test
    public void submitTask() throws Exception
    {
        String param ="{\"taskName\":\"test\",\"param\":\"[\"url\":\"http://111.baidu.com\"]\"}";
        String url="/task/submitTask.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(url).param("param",param)
        ).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));

    }
}
