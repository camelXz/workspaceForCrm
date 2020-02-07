package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.workbench.dao.StuDao;
import com.wkcto.crm.workbench.domain.Stu;
import com.wkcto.crm.workbench.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class StuServiceImpl implements StuService {
    @Autowired
    public StuDao stuDao;
    @Override
    public void addStu(Stu stu) {
        stuDao.addStu(stu);
    }

    @Override
    public Map<String, Object> pageList(Map<String, Object> map) {
       List<Stu> slist =stuDao.findStuList(map);
       int total=stuDao.total(map);
       Map<String,Object> map1=new HashMap<>();
       map1.put("slist", slist);
       map1.put("total", total);
       return map1;
    }
}
