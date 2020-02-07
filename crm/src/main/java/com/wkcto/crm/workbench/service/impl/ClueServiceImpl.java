package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.settings.dao.UserDao;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.utils.DateTimeUtil;
import com.wkcto.crm.utils.HandleFlag;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.vo.MyVo;
import com.wkcto.crm.workbench.dao.*;
import com.wkcto.crm.workbench.domain.*;
import com.wkcto.crm.workbench.service.ClueService;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;

    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;

    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private UserDao userDao;


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private ContactsDao contactsDao;

    @Autowired
    private ContactsRemarkDao contactsRemarkDao;

    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Autowired
    private TranDao tranDao;

    @Autowired
    private TranHistoryDao tranHistoryDao;


    @Override
    public void saveClue(Clue clue) {
        clueDao.saveClue(clue);
    }

    @Override
    public MyVo listPage(Map<String, Object> map) {
        List<Clue> clueList = clueDao.listPage(map);
        int tatol = clueDao.tatol(map);
        MyVo<Clue> myVo = new MyVo<>();
        myVo.setAlist(clueList);
        myVo.setTotal(tatol);
        return myVo;
    }

    @Override
    public void delClue(String[] id) {
        clueDao.delClue(id);
        clueRemarkDao.delClueRemark(id);
    }

    @Override
    public Map<String, Object> findClueById(String id) {
        List<User> userList = userDao.getUserList();
        Clue clue = clueDao.findClueById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("clue", clue);
        return map;
    }

    @Override
    public void updateClue(Clue clue) {
        clueDao.updateClue(clue);
    }

    @Override
    public Clue showDetail(String id) {
        Clue clue = clueDao.showDetail(id);
        return clue;
    }

    @Override
    public void delRelationByIdu(String id) {
        clueDao.delRelationByIdu(id);
    }

    @Override
    public void saveRealtion(String clueId, String[] activityIds) {
        List<ClueActivityRelation> list = new ArrayList<>();
        for (String activityId : activityIds) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(activityId);

            list.add(clueActivityRelation);
        }
        clueActivityRelationDao.saveRealtion(list);
    }

    @Override
    public List<Activity> findActivityByName(String name) {
        List<Activity> activityList = clueDao.findActivityByName(name);
        return activityList;
    }

    @Override
    public void convert(String clueId, Tran t, String createBy, String flag) {
        //得到要转换的线索
        Clue clue = clueDao.findClueById(clueId);

        //根据线索创建客户，首先判断客户是否存在，不存在新建一个
        String name = clue.getCompany();
        Customer customer = customerDao.findCustomerByName(name);
        if (customer == null) {
            customer = new Customer();

            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(name);
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setAddress(clue.getAddress());

            customerDao.saveCustomer(customer);
        }

        //根据线索创建联系人 ，联系人不需要检查重复
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setCreateBy(createBy);
        con.setCreateTime(DateTimeUtil.getSysTime());
        con.setSource(clue.getSource());
        con.setOwner(clue.getOwner());
        con.setNextContactTime(clue.getNextContactTime());
        con.setMphone(clue.getMphone());
        con.setJob(clue.getJob());
        con.setFullname(clue.getFullname());
        con.setEmail(clue.getEmail());
        con.setDescription(clue.getDescription());
        con.setCustomerId(customer.getId());
        con.setContactSummary(clue.getContactSummary());
        con.setAppellation(clue.getAppellation());
        con.setAddress(clue.getAddress());
        contactsDao.saveContacts(con);

        //通过id获取到与选中线索有关的所有线索备注 将备注添加到客户备注和联系人备注中
        List<ClueRemark> clueRemarkList = clueRemarkDao.findClueRemarkByClueId(clueId);
        if (clueRemarkList != null && clueRemarkList.size() > 0) {
            for (ClueRemark clueRemark : clueRemarkList) {
                String noteContent = clueRemark.getNoteContent();
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setCreateBy(createBy);
                customerRemark.setCreateTime(DateTimeUtil.getSysTime());
                customerRemark.setNoteContent(noteContent);
                customerRemark.setEditFlag("0");
                customerRemark.setCustomerId(customer.getId());
                customerRemarkDao.saveCustomerRemark(customerRemark);//将线索备注存入客户的备注中

                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
                contactsRemark.setNoteContent(noteContent);
                contactsRemark.setEditFlag("0");
                contactsRemark.setContactsId(con.getId());
                contactsRemarkDao.saveContactsRemark(contactsRemark);//将线索备注存入联系人的备注中

                //将线索活动对应的表中信息存入到联系人与客户的表中
                List<ClueActivityRelation> clueactivityRelationList = clueActivityRelationDao.findClueActivityRelationByClueId(clueId);

                for (ClueActivityRelation clueActivityRelation : clueactivityRelationList) {
                    String activityId = clueActivityRelation.getActivityId();
                    ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
                    contactsActivityRelation.setId(UUIDUtil.getUUID());
                    contactsActivityRelation.setActivityId(activityId);
                    contactsActivityRelation.setContactsId(con.getId());
                    contactsActivityRelationDao.saveRelation(contactsActivityRelation);//存入表中
                }

                //如果前端有需求，创建交易那么就创建交易和交易记录

                if ("a".equals(flag)) {
                    t.setSource(clue.getSource());
                    t.setOwner(clue.getOwner());
                    t.setNextContactTime(clue.getNextContactTime());
                    t.setCustomerId(customer.getId());
                    t.setDescription(clue.getDescription());
                    t.setContactSummary(clue.getContactSummary());
                    t.setContactsId(con.getId());
                    tranDao.saveTran(t);

                    //生成交易历史
                    TranHistory tranHistory = new TranHistory();
                    tranHistory.setId(UUIDUtil.getUUID());
                    tranHistory.setMoney(t.getMoney());
                    tranHistory.setExpectedDate(t.getExpectedDate());
                    tranHistory.setStage(t.getStage());
                    tranHistory.setCreateBy(createBy);
                    tranHistory.setCreateTime(DateTimeUtil.getSysTime());
                    tranHistory.setTranId(t.getId());

                    //创建交易历史
                    tranHistoryDao.saveHistory(tranHistory);
                }

                //删除线索关联的备注信息
               clueRemarkDao.delClueRemarkByClueId(clueId);
                //删除线索关联的线索活动信息
               clueActivityRelationDao.delByClueId(clueId);
                //删除线索信息
                clueDao.delClueById(clueId);



            }
        }


    }

}
