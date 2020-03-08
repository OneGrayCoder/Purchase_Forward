package com.aki.purchaseforward.service;

import com.aki.purchaseforward.pojo.User;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:00
 */
public interface UserService {

    boolean checkUser(User user);

    List<User> findAllUser();

    //查找所有用户
    int userNumber();

    boolean addUser(User user);
}
