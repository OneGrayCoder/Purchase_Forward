package com.aki.purchaseforward.dao;

import com.aki.purchaseforward.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 13:14
 */

//接口作为一个公共模板,需要方法,直接在这里添加
//然后也可以用接口创建对象
public interface UserDao {
    //regist方法
    boolean regist(User user);

    String securityPassword(String password);

    boolean checkName(String userName);

    boolean User_Login(User user);
}
