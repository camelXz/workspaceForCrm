package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.Student;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface StudentDao {
    void saveStudent(Student student);

    List<Student> pageList(Map<String, Object> map);

    int total(Map<String, Object> map);
}
