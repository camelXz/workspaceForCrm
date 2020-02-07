package com.wkcto.crm.workbench.service.impl;

import com.wkcto.crm.settings.dao.UserDao;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.utils.DateTimeUtil;
import com.wkcto.crm.utils.UUIDUtil;
import com.wkcto.crm.workbench.dao.CustomerDao;
import com.wkcto.crm.workbench.dao.TranDao;
import com.wkcto.crm.workbench.dao.TranHistoryDao;
import com.wkcto.crm.workbench.domain.Contacts;
import com.wkcto.crm.workbench.domain.Customer;
import com.wkcto.crm.workbench.domain.Tran;
import com.wkcto.crm.workbench.domain.TranHistory;
import com.wkcto.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.List;

/**
 * 动力节点7777
 */
@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }

    @Override
    public List<Contacts> findContactsByName(String fullname) {
        List<Contacts> contactsList = tranDao.findContactsByName(fullname);
        return contactsList;
    }

    @Override
    public void saveTransaction(Tran t, String customerName) {
        Customer customer = customerDao.findCustomerByName(customerName);
        if (customer == null) {
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setCreateBy(t.getCreateBy());
            customer.setOwner(t.getOwner());
            customer.setNextContactTime(t.getNextContactTime());
            customer.setDescription(t.getDescription());
            customer.setContactSummary(t.getContactSummary());

            customerDao.saveCustomer(customer);
        }

        t.setCustomerId(customer.getId());
        tranDao.saveTransaction(t);

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setMoney(t.getMoney());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setStage(t.getStage());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setTranId(t.getId());
        tranHistoryDao.saveHistory(tranHistory);
    }

    @Override
    public Tran findTranById(String id) {
        Tran tran= tranDao.findTranById(id);
        return tran;
    }
}
