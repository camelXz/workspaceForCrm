package com.wkcto.crm.workbench.web.controller;

import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.utils.DateTimeUtil;
import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Tran;
import com.wkcto.crm.workbench.service.CustomerService;
import com.wkcto.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/toIndex.do")
    public String toIndex() {
        return "workbench/transaction/index";
    }

    @RequestMapping("/toSave.do")
    public ModelAndView toSave() {
        List<User> ulist = tranService.getUserList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("ulist", ulist);
        modelAndView.setViewName("workbench/transaction/save");
        return modelAndView;
    }

    @RequestMapping("/getCustomerNameListByName")
    @ResponseBody
    public List<String> getCustomerNameListByName(String name) {
        List<String> list = customerService.getCustomerNameListByName(name);
        return list;

    }
    @RequestMapping("/findContactsByName.do")
    @ResponseBody
    public List<Contacts> findContactsByName(String fullname){
        List<Contacts> contactsList=tranService.findContactsByName(fullname);
        return contactsList;
    }
    @RequestMapping("/saveTransaction.do")
    public String saveTransaction(Tran t, HttpSession session,String customerName){
        t.setId(UUIDUtil.getUUID());
        t.setCreateBy(((User)(session.getAttribute("user"))).getName());
        t.setCreateTime(DateTimeUtil.getSysTime());
        tranService.saveTransaction(t,customerName);
        return "redirect:/workbench/transaction/toIndex.do";
    }
    @RequestMapping("/showPossibility.do")
    @ResponseBody
    public Map<String,String> showPossibility(String stage, HttpServletRequest request){
        ServletContext application = request.getServletContext();
        Map<String,Object> map = (Map<String, Object>) application.getAttribute("pMap");
        String  o = (String) map.get(stage);
        Map<String,String> map1=new HashMap<>();
        map1.put("possibility", o);
        return map1;
    }
    @RequestMapping("/toDetail.do")
    public ModelAndView toDetail(String id,HttpServletRequest request){
        ServletContext application = request.getServletContext();
       Map<String,Object> map= (Map<String, Object>) application.getAttribute("pMap");
        Tran tran= tranService.findTranById(id);
        String p = (String) map.get(tran.getStage());
        tran.setPossibility(p);
        ModelAndView modelAndView=new ModelAndView();
       modelAndView.addObject("t", tran);
       modelAndView.setViewName("workbench/transaction/detail");
       return modelAndView;
    }
}
