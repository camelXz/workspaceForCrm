package com.wkcto.crm.web.listener;

import com.sun.org.apache.xpath.internal.operations.Div;
import com.wkcto.crm.settings.domain.DicType;
import com.wkcto.crm.settings.domain.DicValue;
import com.wkcto.crm.settings.service.DicService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * 动力节点7777
 */
public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        //处理数据字典 讲数据字典存入全局作用域中来使用
        ServletContext application = event.getServletContext();
       DicService dicService=WebApplicationContextUtils.getWebApplicationContext(application).getBean(DicService.class);
       Map<String, List<DicValue>> map= dicService.getAll();
        Set<String> keySet = map.keySet();
        for (String s : keySet) {
            List<DicValue> dicValueList = map.get(s);
            application.setAttribute(s, dicValueList);
        }
        //处理可能性与阶段的关系
       ResourceBundle rb= ResourceBundle.getBundle("properties/Stage2Possibility");
        Enumeration<String> e = rb.getKeys();
        Map<String,String> pMap=new HashMap<>();
        while (e.hasMoreElements()){
            String key=e.nextElement();
            String value = rb.getString(key);
            System.out.println("key:"+key+",value:"+value);
            pMap.put(key, value);
        }
        application.setAttribute("pMap", pMap);
    }
}
