package com.wkcto.crm.test;

import com.wkcto.crm.exception.InterceptorException;
import com.wkcto.crm.utils.MD5Util;
import org.junit.Test;

import java.util.UUID;

/**
 * 蛙课网
 */
public class Test01 {

   @Test
    public void test(){
       String md5 = MD5Util.getMD5("123");
       System.out.println(md5);
   }
}
