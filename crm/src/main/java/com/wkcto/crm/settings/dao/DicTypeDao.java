package com.wkcto.crm.settings.dao;

import com.wkcto.crm.settings.domain.DicType;

import java.util.List;

/**
 * 动力节点7777
 */
public interface DicTypeDao {
    List<DicType> getDiceType();

    int findSameDicType(String code);

    void saveType(DicType dicType);

    DicType findDicType(String code);

    void updateDicType(DicType dicType);

    void delDicType(String[] code);


}
