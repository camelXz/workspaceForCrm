package com.wkcto.crm.settings.service.impl;


import com.wkcto.crm.exception.LoginException;
import com.wkcto.crm.settings.dao.UserDao;
import com.wkcto.crm.settings.domain.User;
import com.wkcto.crm.settings.service.UserService;
import com.wkcto.crm.settings.web.controller.State;
import com.wkcto.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动力节点7777
 */
@Service
public class UserServiceImpl implements UserService {
   @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException {
        Map<String ,Object> map=new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (user==null){
            throw new LoginException("账号密码错误");
        }
        String time = user.getExpireTime();
        if (time.compareTo(DateTimeUtil.getSysTime())<0){
            throw new LoginException("账号失效");
        }
        if (State.STATE_CLOSE.equals(user.getLockState())){
            throw new LoginException("账号被锁定！请联系管理员");
        }
        if (!user.getAllowIps().contains(ip)){
            throw new LoginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
       List<User> userList= userDao.getUserList();
       return userList;
    }
}
