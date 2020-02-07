package com.wkcto.crm.settings.dao;

import com.wkcto.crm.settings.domain.DicType;
import com.wkcto.crm.settings.domain.DicValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动力节点7777
 */
public interface DicValueDao {
    void delDicValue(String[] code);

    List<DicValue> findDicValue();

    void saveDicValue(DicValue dicValue);
    List<DicType> initType();

    int findSameDicValueText(@Param(value ="value") String value, @Param(value ="typeCode")String typeCode);

    void delDicValueById(String[] id);

    DicValue findDicValueById(String id);

    void updateDicValueById(DicValue dicValue);

    List<DicValue> findDicValueByType(String code);
}
