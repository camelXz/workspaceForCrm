package com.wkcto.crm.settings.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.wkcto.crm.settings.dao.DicTypeDao;
import com.wkcto.crm.settings.dao.DicValueDao;
import com.wkcto.crm.settings.domain.DicType;
import com.wkcto.crm.settings.domain.DicValue;
import com.wkcto.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public List<DicType> getDiceType() {
        List<DicType> list = dicTypeDao.getDiceType();
        return list;
    }

    @Override
    public Map<String, Object> findSameDicType(String code) {
        int rows = dicTypeDao.findSameDicType(code);
        boolean flag = true;
        Map<String, Object> map = new HashMap<>();
        if (rows == 0) {
            map.put("success", flag);
            return map;
        } else {
            flag = false;
            map.put("success", flag);
            return map;
        }

    }

    @Override
    public void saveType(DicType dicType) {
        dicTypeDao.saveType(dicType);
    }

    @Override
    public DicType findDicType(String code) {
        DicType dicType = dicTypeDao.findDicType(code);
        return dicType;
    }

    @Override
    public void updateDicType(DicType dicType) {
        dicTypeDao.updateDicType(dicType);
    }

    @Override
    public void delDicType(String[] code) {
        dicTypeDao.delDicType(code);
    }

    @Override
    public void delDicValue(String[] code) {
        dicValueDao.delDicValue(code);
    }

    @Override
    public List<DicValue> findDicValue() {
        List<DicValue> list = dicValueDao.findDicValue();
        return list;
    }

    @Override
    public void saveDicValue(DicValue dicValue) {
        dicValueDao.saveDicValue(dicValue);
    }

    @Override
    public List<DicType> initType() {
        List<DicType> list = dicValueDao.initType();
        return list;
    }

    @Override
    public Map<String, Object> findSameDicValueText(String value, String typeCode) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = true;
        int count = dicValueDao.findSameDicValueText(value, typeCode);
        if (count == 0) {
            map.put("success", flag);
        } else {
            flag = false;
            map.put("success", flag);
        }
        return map;
    }

    @Override
    public void delDicValueById(String[] id) {
        dicValueDao.delDicValueById(id);
    }

    @Override
    public DicValue findDicValueById(String id) {
        DicValue dicValue = dicValueDao.findDicValueById(id);
        return dicValue;
    }

    @Override
    public void updateDicValueById(DicValue dicValue) {
        dicValueDao.updateDicValueById(dicValue);
    }

    @Override
    public Map<String, List<DicValue>> getAll() {
        List<DicType> diceTypeList = dicTypeDao.getDiceType();
        Map<String, List<DicValue>> map = new HashMap<>();
        for (DicType dicType : diceTypeList) {
            List<DicValue> dicValueList = dicValueDao.findDicValueByType(dicType.getCode());
            map.put(dicType.getCode()+ "List", dicValueList);
        }
        return map;
    }
}
