package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    List<String> getCustomerNameListByName(String name);

    Customer findCustomerByName(String name);

    void saveCustomer(Customer customer);
}
