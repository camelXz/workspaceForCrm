package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.dao.ActivityDao;
import com.wkcto.crm.workbench.dao.ActivityRemarkDao;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.ActivityRemark;
import com.wkcto.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRemarkDao activityRemarkDao;
    @Autowired
    private ActivityDao activityDao;

    @Override
    public List<User> findAllUser() {
        List<User> list = activityDao.findAllUser();
        return list;
    }

    @Override
    public void saveActivity(Activity activity) {
        activityDao.saveActivity(activity);
    }

    @Override
    public Map<String, Object> pageList(Map<String, Object> map) {
        List<Activity> alist = activityDao.pageList(map);
        int total = activityDao.getTotal(map);
        Map<String, Object> mapall = new HashMap<>();
        mapall.put("alist", alist);
        mapall.put("total", total);
        return mapall;
    }

    public MyVo pageList2(Map<String, Object> map) {
        List<Activity> alist = activityDao.pageList(map);
        int total = activityDao.getTotal(map);
        MyVo<Activity> myVo = new MyVo<>();
        myVo.setAlist(alist);
        myVo.setTotal(total);
        return myVo;
    }

    @Override
    public Activity findActivity(String id) {
        Activity activity = activityDao.findActivity(id);
        return activity;
    }


    @Override
    public void delActivityById(String[] ids) {
        activityDao.delActivityById(ids);
    }

    @Override
    public void delActivityRemarkById(String[] ids) {
        activityRemarkDao.delActivityRemark(ids);
    }

    @Override
    public void updateActivity(Activity activity) {
        activityDao.updateActivity(activity);
    }

    @Override
    public Map<String, Object> findActivityById(String id) {
        List<User> allUser = activityDao.findAllUser();
        Activity activity = activityDao.findActivity(id);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", allUser);
        map.put("activity", activity);
        return map;
    }

    @Override
    public List<Activity> findAllActivity() {
       List<Activity> alist=activityDao.findAllActivity();
       return alist;
    }

    @Override
    public List<Activity> findSomeActivityById(String[] ids) {
        List<Activity> alist=activityDao.findSomeActivityById(ids);
        return alist;
    }

    @Override
    public int importActivityById(List<Activity> alist) {
        int count= activityDao.importActivityById(alist);
        return count;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> findRemarkList(String id) {
       List<ActivityRemark> activityRemarksList= activityRemarkDao.findRemarkList(id);
       return activityRemarksList;
    }

    @Override
    public void deleteRemark(String id) {
       activityRemarkDao.deleteRemark(id);
    }

    @Override
    public void saveActivityRemark(ActivityRemark activityRemark) {
        activityRemarkDao.saveActivityRemark(activityRemark);
    }

    @Override
    public void updateRemark(ActivityRemark activityRemark) {
        activityRemarkDao.updateRemark(activityRemark);
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
       List<Activity> activityList= activityDao.getActivityListByClueId(clueId);
       return activityList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotHave(Map<String, Object> map) {
      List<Activity> activityList=   activityDao.getActivityListByNameAndNotHave(map);
      return activityList;
    }

}
