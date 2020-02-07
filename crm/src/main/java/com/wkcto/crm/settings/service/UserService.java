package com.wkcto.crm.settings.service;


import com.wkcto.crm.exception.LoginException;
import com.wkcto.crm.settings.domain.User;

import java.util.List;

/**
 * 动力节点7777
 */

public interface UserService {
    User login(String loginAct,String loginPwd,String ip) throws LoginException;

    List<User> getUserList();
}
