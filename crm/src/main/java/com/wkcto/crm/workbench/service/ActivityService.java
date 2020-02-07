package com.wkcto.crm.workbench.service;

import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface ActivityService {
    List<User> findAllUser();

    void saveActivity(Activity activity);

    Map<String, Object> pageList(Map<String, Object> map);

    Activity findActivity(String id);
    MyVo pageList2(Map<String, Object> map);
    void delActivityById(String[] ids);
    void delActivityRemarkById(String[] ids);

    void updateActivity(Activity activity);

    Map<String, Object> findActivityById(String id);

    List<Activity> findAllActivity();

    List<Activity> findSomeActivityById(String[] ids);

    int importActivityById(List<Activity> alist);

    Activity detail(String id);

    List<ActivityRemark> findRemarkList(String id);

    void deleteRemark(String id);

    void saveActivityRemark(ActivityRemark activityRemark);

    void updateRemark(ActivityRemark activityRemark);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotHave(Map<String, Object> map);
}
