package com.wkcto.crm.workbench.web.controller;

import com.sun.org.apache.regexp.internal.RE;
import com.wkcto.crm.settings.dao.UserDao;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.settings.service.UserService;
import com.wkcto.crm.utils.DateTimeUtil;
import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.dao.ContactsDao;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.Clue;
import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Tran;
import com.wkcto.crm.workbench.service.ActivityService;
import com.wkcto.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClueService clueService;
    @RequestMapping("/toIndex.do")
    public String toIndex(){
        return "workbench/clue/index";
    }
    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> userList= userService.getUserList();
        return userList;
    }
    @RequestMapping("/saveClue.do")
    @ResponseBody
    public Map<String,Object> saveClue(Clue clue, HttpSession session){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy( ((User)session.getAttribute("user")).getName());
        clueService.saveClue(clue);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/ListPage.do")
    @ResponseBody
    public MyVo  listPage(@RequestParam Map<String,Object> map){
       String pageNostr=(String)map.get("pageNo");
       String pageSizestr=(String)map.get("pageSize");
        int pageNo= Integer.valueOf(pageNostr);
        int pageSize= Integer.valueOf(pageSizestr);
        int skipCount=(pageNo-1)*pageSize;
        map.put("pageSize", pageSize);
        map.put("skipCount", skipCount);
        MyVo myVo=clueService.listPage(map);
        return myVo;
    }
    @RequestMapping("/delClue.do")
    @ResponseBody
    public Map<String,Object> delClue(String[] ids){
        clueService.delClue(ids);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/findClueById.do")
    @ResponseBody
    public Map<String,Object> findClueById(String id){
       Map<String,Object> map= clueService.findClueById(id);
       return map;
    }
    @RequestMapping("/updateClue.do")
    @ResponseBody
    public Map<String,Object> updateClue(Clue clue, HttpSession session){
        clue.setEditTime(DateTimeUtil.getSysTime());
        clue.setEditBy(((User)session.getAttribute("user")).getName());
        clueService.updateClue(clue);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/showDetail.do")
    public ModelAndView showDetail(String id){
        Clue clue= clueService.showDetail(id);
        ModelAndView mav=new ModelAndView();
        mav.addObject("c", clue);
        mav.setViewName("workbench/clue/detail");
        return mav;
    }
    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByClueId(String clueId){
       List<Activity> activityList= activityService.getActivityListByClueId(clueId);
       return activityList;
    }
    @RequestMapping("/unbund.do")
    @ResponseBody
    public Map<String,Object> unbund(String id){
        clueService.delRelationByIdu(id);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/getActivityListByNameAndNotHave.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotHave(@RequestParam Map<String,Object> map){
        List<Activity> activityList= activityService.getActivityListByNameAndNotHave(map);
        return activityList;

    }
    @RequestMapping("/bindingClue.do")
    @ResponseBody
    public Map<String,Object> bindingClue(String clueId,String[] activityIds){
         clueService.saveRealtion(clueId,activityIds);
         return HandleFlag.successTrue();
    }
    @RequestMapping("/toConvert.do")
    public ModelAndView toConvert(@RequestParam Map<String,Object> map){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("xx",map);
        modelAndView.setViewName("workbench/clue/convert");
        return modelAndView;
    }
    @RequestMapping("/findActivityByName.do")
    @ResponseBody
    public  List<Activity> findActivityByName(String name){
        List<Activity> activityList= clueService.findActivityByName(name);
        return activityList;
    }
    @RequestMapping("/convert.do")
    public String convert(String clueId, Tran t,String flag,HttpSession session){
        String createBy = ((User)session.getAttribute("user")).getName();
        if ("a".equals(flag)){
            t.setId(UUIDUtil.getUUID());
            String createTime = DateTimeUtil.getSysTime();
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }
        clueService.convert(clueId,t,createBy,flag);
        return"redirect:/workbench/clue/toIndex.do";
    }
}
