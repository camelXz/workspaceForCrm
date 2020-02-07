package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * 动力节点7777
 */
public interface ActivityRemarkDao {
    void delActivityRemark(String[] ids);

    List<ActivityRemark> findRemarkList(String id);

    void deleteRemark(String id);

    void saveActivityRemark(ActivityRemark activityRemark);

    void updateRemark(ActivityRemark activityRemark);
}
