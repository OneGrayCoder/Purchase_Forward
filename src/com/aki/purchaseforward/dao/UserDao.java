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

    //将数据库中的一些常规操作抽出到接口中
/*    ResultSet query(PreparedStatement pre, Object ...obj);
    int update(PreparedStatement pre, Object ...obj);
    void close(PreparedStatement pre,Connection con);
    void close(ResultSet rs,PreparedStatement pre,Connection con);*/

    boolean checkUser(User user);

    List<User> findUser();

    int userNumber();

    boolean addUsers(User user);

}
