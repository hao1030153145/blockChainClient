package com.smartdatachain.workflow.util;

import com.jeeframework.testframework.AbstractSpringBaseTestNoTransaction;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by byron on 2018/4/4 0004.
 */
public class BaseKafkaConsumerTest extends AbstractSpringBaseTestNoTransaction{
    @Autowired


    @Test
    public void dealMessage() throws Exception {
        while (true) {
            Thread.sleep(1000);
        }
    }

}
