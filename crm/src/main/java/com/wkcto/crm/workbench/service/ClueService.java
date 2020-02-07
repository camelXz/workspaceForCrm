package com.wkcto.crm.workbench.service;

import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.domain.Activity;
import com.wkcto.crm.workbench.domain.Clue;
import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
public interface ClueService {
    void saveClue(Clue clue);

    MyVo listPage(Map<String, Object> map);

    void delClue(String[] ids);
    Map<String ,Object> findClueById(String id);

    void updateClue(Clue clue);

    Clue showDetail(String id);

    void delRelationByIdu(String id);

    void saveRealtion(String clueId, String[] activityIds);

    List<Activity> findActivityByName(String name);

    void convert(String clueId, Tran t, String createBy, String flag);

}
