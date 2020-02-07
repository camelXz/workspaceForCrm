package com.wkcto.crm.settings.web.controller;

import com.wkcto.crm.settings.domain.DicType;
import com.wkcto.crm.settings.domain.DicValue;
import com.wkcto.crm.settings.service.DicService;
import com.wkcto.crm.utils.UUIDUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {
    @Autowired
    private DicService dicService;

    @RequestMapping("/toindex.do")
    public String toIndex() {
        return "settings/dictionary/index";
    }

    @RequestMapping("/type/toTypeIndex.do")
    public ModelAndView toTypeIndex() {
        List<DicType> list = dicService.getDiceType();
        ModelAndView md = new ModelAndView();
        md.addObject("diclist", list);
        md.setViewName("settings/dictionary/type/index");
        return md;
    }

    @RequestMapping("/type/toTypeIndex2.do")
    public String toTypeIndex2(HttpServletRequest request) {
        List<DicType> list = dicService.getDiceType();
        request.setAttribute("list", list);
        return "settings/dictionary/type/index";

    }

    @RequestMapping("/type/toDicSave.do")
    public String toDicSave() {
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/type/findSameDicType.do")
    @ResponseBody
    public Map<String, Object> findSameDicType(String code) {
        Map<String, Object> map = dicService.findSameDicType(code);
        return map;
    }

    @RequestMapping("/type/saveType.do")
    public String saveType(DicType dicType) {
        dicService.saveType(dicType);
        return "redirect:/settings/dictionary/type/toTypeIndex.do";
    }

    @RequestMapping("/type/toTypeUpdate.do")
    public ModelAndView toTypeUpdate(String code) {
        DicType dicType = dicService.findDicType(code);
        ModelAndView mav = new ModelAndView();
        mav.addObject("tylist", dicType);
        mav.setViewName("settings/dictionary/type/edit");
        return mav;
    }

    @RequestMapping("/type/updateDicType.do")
    public String updateDicType(DicType dicType) {
        dicService.updateDicType(dicType);
        return "redirect:/settings/dictionary/type/toTypeIndex.do";
    }

    @RequestMapping("/type/delDicType.do")
    public String delDicType(String[] code) {
        dicService.delDicValue(code);
        dicService.delDicType(code);
        return "redirect:/settings/dictionary/type/toTypeIndex.do";
    }

    @RequestMapping("/value/toValueIndex.do")
    public ModelAndView toValueIndex() {
        List<DicValue> list = dicService.findDicValue();
        ModelAndView mav = new ModelAndView();
        mav.addObject("vulist", list);
        mav.setViewName("settings/dictionary/value/index");
        return mav;
    }

    @RequestMapping("/value/initType.do")
    public ModelAndView initType() {
        List<DicType> list = dicService.initType();
        ModelAndView mav = new ModelAndView();
        mav.addObject("tplist", list);
        mav.setViewName("settings/dictionary/value/save");
        return mav;
    }

    @RequestMapping("/value/saveDicValue.do")
    public String saveDicValue(DicValue dicValue) {
        String uuid = UUIDUtil.getUUID();
        dicValue.setId(uuid);
        dicService.saveDicValue(dicValue);
        return "redirect:/settings/dictionary/value/toValueIndex.do";
    }

    @RequestMapping(value = "/value/findSameDicValueText.do", consumes = "application/json")
    @ResponseBody
    public Map<String, Object> findSameDicValueText(String value,String typeCode) {
         Map<String, Object> map= dicService.findSameDicValueText(value, typeCode);
        return map;
    }

    @RequestMapping("/value/delDicValueById.do")
    public String delDicValueById(String[] id) {
        dicService.delDicValueById(id);
        return "redirect:/settings/dictionary/value/toValueIndex.do";
    }

    @RequestMapping("/value/toUpdateDicValue.do")
    public ModelAndView toUpdateDicValue(String id) {
        DicValue dicValue = dicService.findDicValueById(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("dicValue", dicValue);
        mav.setViewName("settings/dictionary/value/edit");
        return mav;
    }

    @RequestMapping("/value/updateDicValueById.do")
    public String updateDicValueById(DicValue dicValue) {
        System.out.println(dicValue);
        dicService.updateDicValueById(dicValue);
        return "redirect:/settings/dictionary/value/toValueIndex.do";
    }

}
