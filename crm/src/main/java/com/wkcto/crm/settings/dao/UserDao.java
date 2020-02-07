package com.wkcto.crm.settings.dao;/*
/**
 * 动力节点7777
 */

import com.wkcto.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, Object> map);

    List<User> getUserList();
}
