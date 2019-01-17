package com.smartdatachain.workflow.util;

import com.smartdatachain.api.util.ExcelUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertTrue;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2017-02-26 21:01
 */
public class ExcelUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void combinateStaticURL() throws Exception {
//        List<String[]> list = ExcelUtil.readExcel("D:\\document\\excel\\第一财经_财经650 - 2003.xls");
        int num  =  ExcelUtil.readExcelRowNum("D:\\document\\excel\\新浪行业-国产新车（Alex）500车市.xlsx");
        System.out.println(num);
    }

}