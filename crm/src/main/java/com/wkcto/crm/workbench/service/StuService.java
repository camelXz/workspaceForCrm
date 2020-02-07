package com.wkcto.crm.workbench.service;

import com.wkcto.crm.workbench.domain.Stu;

import java.util.Map;

/**
 * 动力节点7777
 */
public interface StuService {
    void addStu(Stu stu);

    Map<String, Object> pageList(Map<String, Object> map);
}
