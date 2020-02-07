package com.wkcto.crm.workbench.web.controller;

import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.workbench.domain.Stu;
import com.wkcto.crm.workbench.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/workbench/stu")
public class StuController {
    @Autowired
    public StuService stuService;
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "workbench/stu/indexForTest";
    }
    @RequestMapping("/addStu.do")
    @ResponseBody
    public Map<String,Object> addStu(Stu stu){
        stu.setId(UUIDUtil.getUUID());
        stuService.addStu(stu);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String,Object> pageList(@RequestParam Map<String,Object> map){
        String pageNoStr=(String) map.get("pageNo");
        String pageSizeStr=(String)map.get("pageSize");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        Map<String,Object> map1= stuService.pageList(map);
        return map1;

    }
}
