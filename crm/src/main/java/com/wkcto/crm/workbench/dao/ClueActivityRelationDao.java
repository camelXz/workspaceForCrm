package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    void saveRealtion(List<ClueActivityRelation> list);

    List<ClueActivityRelation> findClueActivityRelationByClueId(String clueId);

    void delByClueId(String clueId);
}
