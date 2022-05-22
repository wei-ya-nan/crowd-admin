package com.wyn.trest;

import com.wyn.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/15
 */
public class UtilTest {


    @Test
    public void testEncode(){
        String resource = "123123";
        String s = CrowdUtil.md5(resource);
        System.out.println(s);
    }
}
