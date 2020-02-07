package com.wkcto.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {
    @RequestMapping("/toindex.do")
    public String toIndex(){
        return "settings/index";
    }
}
