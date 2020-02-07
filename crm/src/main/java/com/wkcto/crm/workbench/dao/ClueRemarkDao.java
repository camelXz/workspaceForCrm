package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

     void delClueRemark(String[] id);

    List<ClueRemark> findClueRemarkByClueId(String clueId);

    void delClueRemarkByClueId(String clueId);
}
