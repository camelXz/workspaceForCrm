package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface ActivityDao {
    List<User> findAllUser();

    void saveActivity(Activity activity);

    List<Activity> pageList(Map<String, Object> map);

    int getTotal(Map<String, Object> map);

    Activity findActivity(String id);

    void delActivityById(String[] ids);

    void updateActivity(Activity activity);

    List<Activity> findAllActivity();

    List<Activity> findSomeActivityById(String[] ids);

    int importActivityById(List<Activity> alist);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotHave(Map<String, Object> map);
}
