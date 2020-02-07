package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.workbench.dao.CustomerDao;
import com.wkcto.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动力节点7777
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<String> getCustomerNameListByName(String name) {
        List<String> list = customerDao.getCustomerNameListByName(name);
        if (list==null){
            System.out.println("--------------------------------------------------------------");
        }
        return list;
    }
}
