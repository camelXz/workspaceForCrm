package com.wkcto.crm.settings.service;

import com.wkcto.crm.settings.domain.DicType;
import com.wkcto.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface DicService {
    List<DicType> getDiceType();

    Map<String,Object> findSameDicType(String code);

    void saveType(DicType dicType);

    DicType findDicType(String code);

    void updateDicType(DicType dicType);

    void delDicType(String[] args);

    void delDicValue(String[] code);

    List<DicValue> findDicValue();

    void saveDicValue(DicValue dicValue);

    List<DicType> initType();

    Map<String ,Object> findSameDicValueText(String value, String typeCode);

    void delDicValueById(String[] id);

    DicValue findDicValueById(String id);

    void updateDicValueById(DicValue dicValue);

    Map<String, List<DicValue>> getAll();
}
