package com.aki.purchaseforward.service;

import com.aki.purchaseforward.pojo.User;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:00
 */
public interface UserService {


    boolean regist(User user);

    String securityPass(String password);


    //检查是否重名
    boolean checkName(String username);


    boolean checkLogin(User user);

}
