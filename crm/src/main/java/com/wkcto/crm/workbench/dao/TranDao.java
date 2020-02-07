package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    void saveTran(Tran t);

    List<Contacts> findContactsByName(String fullname);

    void saveTransaction(Tran t);

    Tran findTranById(String id);
}
