package com.wkcto.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/workbench")
public class workbenchController {

    @RequestMapping("/toWorkbenchIndex.do")
    public String toWorkbenchIndex(){
        return "workbench/index";
    }
@RequestMapping("/main/toMainIndex.do")
    public String toMainIndex(){
        return "workbench/main/index";
    }

}
