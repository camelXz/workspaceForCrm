package com.wkcto.crm.workbench.dao;

import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {


    void saveClue(Clue clue);

    List<Clue> listPage(Map<String, Object> map);

    int tatol(Map<String, Object> map);

    void delClue(String[] id);

    Clue findClueById(String id);

    void updateClue(Clue clue);

    Clue showDetail(String id);

    void delRelationByIdu(String id);

    List<Activity> findActivityByName(String name);

    void delClueById(String id);
}
