package com.wkcto.crm.workbench.service;

import com.wkcto.crm.workbench.domain.Student;

import java.util.Map;

/**
 * 动力节点7777
 */
public interface StudentService {
    void saveStudent(Student student);

    Map<String, Object> pageList(Map<String, Object> map);
}
