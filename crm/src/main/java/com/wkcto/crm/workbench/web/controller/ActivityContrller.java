package com.wkcto.crm.workbench.web.controller;


import com.wkcto.crm.exception.AjaxRequestException;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.utils.DateTimeUtil;
import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.ActivityRemark;
import com.wkcto.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Controller
@RequestMapping("/workbench/activity")
public class ActivityContrller {
    @Autowired
    private ActivityService activityService;
    @RequestMapping("/toActivityIndex.do")
    public String toActivityIndex(){
        return "workbench/activity/index";
    }
    @RequestMapping("/findAllUser.do")
    @ResponseBody
    public List<User> findAllUser(){
        List<User> list= activityService.findAllUser();
        return list;
    }
    @RequestMapping("/saveActivity.do")
    @ResponseBody
    public Map<String,Object> saveActivity(Activity activity, HttpSession session){
        String name= ((User)session.getAttribute("user")).getName();
        String time = DateTimeUtil.getSysTime();
        activity.setCreateTime(time);
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(name);
        activityService.saveActivity(activity);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/pageList.do")
    @ResponseBody
    public Map<String,Object> pageList(String pageNoStr,String pageSizeStr,String name,
                                       String woner,String startDate,String endDate){
       int pageNo= Integer.valueOf(pageNoStr);
       int pageSize=Integer.valueOf(pageSizeStr);
       int skipCount=(pageNo-1)*pageSize;
       Map<String,Object> map= new HashMap<>();
       map.put("name", name);
       map.put("woner", woner);
       map.put("startDate", startDate);
       map.put("endDate", endDate);
       map.put("pageSize", pageSize);
       map.put("skipCount",skipCount);
       Map<String,Object>amap=activityService.pageList(map);
       return amap;
    }
    @RequestMapping("/findActivityById.do")
    @ResponseBody
    public Map<String,Object> findActivityById(String id){
        Map<String,Object> map = activityService.findActivityById(id);
        return map;
    }
    @RequestMapping("/pageList2.do")
    @ResponseBody
    public MyVo pageList2(@RequestParam Map<String,Object> map){
        String pageNoStr=(String) map.get("pageNoStr");
        String pageSizeStr=(String)map.get("pageSizeStr");
        int pageNo=Integer.valueOf(pageNoStr);
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        map.put("pageSize", pageSize);
        map.put("skipCount",skipCount);
        MyVo myVo = activityService.pageList2(map);
        return myVo;
    }
    @RequestMapping("/delActivityById.do")
    @ResponseBody
    public Map<String,Object>  delActivityById(String[] ids){
        activityService.delActivityRemarkById(ids);
       activityService.delActivityById(ids);
       return HandleFlag.successTrue();
    }
    @RequestMapping("/updateActivity.do")
    @ResponseBody
    public Map<String,Object> updateActivity(Activity activity,HttpSession session){
        activity.setEditTime(DateTimeUtil.getSysTime());
        String name = ((User) session.getAttribute("user")).getName();
        System.out.println(name);
        activity.setEditBy(name);
        activityService.updateActivity(activity);
        return HandleFlag.successTrue();
    }
    @RequestMapping("/exportAllActivity.do")
    public void exportAllActivity(HttpServletResponse response) throws Exception{
        List<Activity> alist=activityService.findAllActivity();
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("活动表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("编号");
        cell= row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");

        cell = row.createCell(3);
        cell.setCellValue("开始日期");

        cell = row.createCell(4);
        cell.setCellValue("结束日期");

        cell = row.createCell(5);
        cell.setCellValue("成本");

        cell = row.createCell(6);
        cell.setCellValue("描述");

        cell = row.createCell(7);
        cell.setCellValue("创建时间");

        cell = row.createCell(8);
        cell.setCellValue("创建人");

        cell = row.createCell(9);
        cell.setCellValue("修改时间");

        cell = row.createCell(10);
        cell.setCellValue("修改人");

        for(int i=0;i<alist.size();i++){

            Activity a = alist.get(i);

            row = sheet.createRow(i+1);

            cell = row.createCell(0);
            cell.setCellValue(a.getId());

            cell = row.createCell(1);
            cell.setCellValue(a.getOwner());

            cell = row.createCell(2);
            cell.setCellValue(a.getName());

            cell = row.createCell(3);
            cell.setCellValue(a.getStartDate());

            cell = row.createCell(4);
            cell.setCellValue(a.getEndDate());

            cell = row.createCell(5);
            cell.setCellValue(a.getCost());

            cell = row.createCell(6);
            cell.setCellValue(a.getDescription());

            cell = row.createCell(7);
            cell.setCellValue(a.getCreateTime());

            cell = row.createCell(8);
            cell.setCellValue(a.getCreateBy());

            cell = row.createCell(9);
            cell.setCellValue(a.getEditTime());

            cell = row.createCell(10);
            cell.setCellValue(a.getEditBy());
        }
       response.setContentType("octets/stream");
        //设置响应头信息：服务器通知浏览器将要以下载的方式来处理响应
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTime()+".xls");

        OutputStream out = response.getOutputStream();

        workbook.write(out);

        workbook.close();
    }
    @RequestMapping("/exportActivityById.do")
    public void exportActivityById(HttpServletResponse response,String[] ids) throws Exception{
        List<Activity> alist=activityService.findSomeActivityById(ids);
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("活动表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("编号");
        cell= row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");

        cell = row.createCell(3);
        cell.setCellValue("开始日期");

        cell = row.createCell(4);
        cell.setCellValue("结束日期");

        cell = row.createCell(5);
        cell.setCellValue("成本");

        cell = row.createCell(6);
        cell.setCellValue("描述");

        cell = row.createCell(7);
        cell.setCellValue("创建时间");

        cell = row.createCell(8);
        cell.setCellValue("创建人");

        cell = row.createCell(9);
        cell.setCellValue("修改时间");

        cell = row.createCell(10);
        cell.setCellValue("修改人");

        for(int i=0;i<alist.size();i++){

            Activity a = alist.get(i);

            row = sheet.createRow(i+1);

            cell = row.createCell(0);
            cell.setCellValue(a.getId());

            cell = row.createCell(1);
            cell.setCellValue(a.getOwner());

            cell = row.createCell(2);
            cell.setCellValue(a.getName());

            cell = row.createCell(3);
            cell.setCellValue(a.getStartDate());

            cell = row.createCell(4);
            cell.setCellValue(a.getEndDate());

            cell = row.createCell(5);
            cell.setCellValue(a.getCost());

            cell = row.createCell(6);
            cell.setCellValue(a.getDescription());

            cell = row.createCell(7);
            cell.setCellValue(a.getCreateTime());

            cell = row.createCell(8);
            cell.setCellValue(a.getCreateBy());

            cell = row.createCell(9);
            cell.setCellValue(a.getEditTime());

            cell = row.createCell(10);
            cell.setCellValue(a.getEditBy());
        }
        response.setContentType("octets/stream");
        //设置响应头信息：服务器通知浏览器将要以下载的方式来处理响应
        response.setHeader("Content-Disposition","attachment;filename=Activity-"+DateTimeUtil.getSysTime()+".xls");

        OutputStream out = response.getOutputStream();

        workbook.write(out);

        workbook.close();
    }
    @RequestMapping("/importActivityById.do")
    @ResponseBody
    public Map<String,Object> importActivityById(@RequestParam("myFile")MultipartFile file,HttpServletRequest request) throws AjaxRequestException {
      try {
          String  filename=DateTimeUtil.getSysTimeForUpload()+".xls";
          String path= request.getServletContext().getRealPath("/uplode");
          file.transferTo(new File(path+"/"+filename));
          InputStream inputStream=new FileInputStream(path+"/"+filename);
          HSSFWorkbook workbook=new HSSFWorkbook(inputStream);
          HSSFSheet sheet = workbook.getSheetAt(0);
          List<Activity> alist=new ArrayList<>();
          for (int i=1;i<sheet.getLastRowNum()+1;i++){
              HSSFRow row = sheet.getRow(i);
              Activity activity=new Activity();
              for (int j=0;j<row.getLastCellNum();j++){
                  HSSFCell cell = row.getCell(j);
                  if (j==0){
                     activity.setId(cell.getStringCellValue());
                  }else if(j==1){

                      activity.setOwner(cell.getStringCellValue());

                  }else if(j==2){

                      activity.setName(cell.getStringCellValue());

                  }else if(j==3){

                      activity.setStartDate(cell.getStringCellValue());

                  }else if(j==4){

                      activity.setEndDate(cell.getStringCellValue());

                  }else if(j==5){

                      activity.setCost(cell.getStringCellValue());

                  }else if(j==6){

                      activity.setDescription(cell.getStringCellValue());

                  }else if(j==7){

                      activity.setCreateTime(cell.getStringCellValue());

                  }else if(j==8){

                      activity.setCreateBy(cell.getStringCellValue());

                  }else if(j==9){

                      activity.setEditTime(cell.getStringCellValue());

                  }else if(j==10){

                      activity.setEditBy(cell.getStringCellValue());
                  }

              }
              alist.add(activity);
          }
          int count=activityService.importActivityById(alist);
          System.out.println(count);
          return HandleFlag.successObj("count", count);
      }catch (Exception e){
          e.printStackTrace();
          throw new AjaxRequestException();
      }
    }
    @RequestMapping("/toDetail.do")
    public ModelAndView toDetail(String id){
        ModelAndView modelAndView=new ModelAndView();
        Activity activity=activityService.detail(id);
        modelAndView.addObject("activity",activity);
        modelAndView.setViewName("workbench/activity/detail");
        return modelAndView;
    }
    @RequestMapping("/findRemarkList.do")
    @ResponseBody
    public List<ActivityRemark> findRemarkList(String id){
        List<ActivityRemark> activityRemarksList= activityService.findRemarkList(id);
        return activityRemarksList;

    }
    @RequestMapping("deleteRemark.do")
    @ResponseBody
    public Map<String,Object> deleteRemark(String id){
        activityService.deleteRemark(id);
       return  HandleFlag.successTrue();
    }
    @RequestMapping("/saveActivityRemark.do")
    @ResponseBody
    public Map<String,Object> saveActivityRemark(ActivityRemark activityRemark,HttpSession session){
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User)session.getAttribute("user")).getName());
        activityRemark.setEditFlag("0");
        activityService.saveActivityRemark(activityRemark);
        return HandleFlag.successObj("ar", activityRemark);
    }
    @RequestMapping("/updateRemark.do")
    @ResponseBody
    public Map<String,Object> updateRemark(ActivityRemark activityRemark,HttpSession session    ){
        activityRemark.setEditFlag("1");
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        activityRemark.setEditBy(((User)session.getAttribute("user")).getName());
        activityService.updateRemark(activityRemark);
        return HandleFlag.successObj("ar", activityRemark);
    }

}
