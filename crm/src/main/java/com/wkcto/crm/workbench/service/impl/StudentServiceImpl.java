package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.workbench.dao.StudentDao;
import com.wkcto.crm.workbench.domain.Student;
import com.wkcto.crm.workbench.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    public StudentDao studentDao;

    @Override
    public void saveStudent(Student student) {
        studentDao.saveStudent(student);
    }

    @Override
    public Map<String, Object> pageList(Map<String, Object> map) {
        List<Student> studentList = studentDao.pageList(map);
        int total = studentDao.total(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("studentList", studentList);
        map1.put("total", total);
        return map1;
    }
}
