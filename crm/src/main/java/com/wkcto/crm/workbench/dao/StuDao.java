package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.Stu;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface StuDao {
    void addStu(Stu stu);

    List<Stu> findStuList(Map<String, Object> map);

    int total(Map<String, Object> map);
}
