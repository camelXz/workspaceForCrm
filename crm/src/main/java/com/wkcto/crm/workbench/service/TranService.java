package com.wkcto.crm.workbench.service;

import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Tran;

import java.util.List;

/**
 * 动力节点7777
 */
public interface TranService {
    List<User> getUserList();

    List<Contacts> findContactsByName(String fullname);

    void saveTransaction(Tran t,String customerName);

    Tran findTranById(String id);
}
