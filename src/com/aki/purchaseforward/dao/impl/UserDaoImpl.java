package com.aki.purchaseforward.dao.impl;

import com.aki.purchaseforward.dao.UserDao;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.User;
import com.aki.purchaseforward.util.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:04
 */
public class UserDaoImpl implements UserDao {
    private BasicDaoUtil basic = new BasicDaoUtil();
    @Override
    public boolean checkUser(User user) {
        boolean res = false;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet query = null;
        String sql = "select count(*) from user where username = ? and pwd = ? and rule = ?";
        try {
            con = DBPool.getConnection();
            pst = con.prepareStatement(sql);
            query = basic.query(pst, user.getUsername(), user.getPwd(),1);
            //判断结果是否为空,是否查询出来结果
            if (query != null&&query.next()){
                if (query.getInt(1)==1){
                    res = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(query,pst,con);
        }
        return res;
    }

    //负责查找所有的用户
    @Override
    public List<User> findUser(){
        List<User> list = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet query = null;
        Connection con = null;
        con = DBPool.getConnection();
        String sql = "select *from user";
        try {
            pst = con.prepareStatement(sql);
            query = basic.query(pst);
           while(query != null&&query.next()){
                User user = new User();
                user.setId(query.getInt(1));
                user.setUsername(query.getString(2));
                user.setPwd(query.getString(3));
                user.setRule(query.getInt(4));
                user.setEmail(query.getString(5));
                user.setQq(query.getString(6));
                list.add(user);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            basic.close(query,pst,con);
        }
        return list;
    }

    @Override
    public int userNumber(){
        PreparedStatement pst = null;
        ResultSet query = null;
        Connection con = null;
        con = DBPool.getConnection();
        int res = 0;
        String sql = "select count(id) from user";
        try {
            pst = con.prepareStatement(sql);
            query = basic.query(pst);
            while(query != null&&query.next()){
                res = query.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            basic.close(query,pst,con);
        }
        return res;
    }

    @Override
    public boolean addUsers(User user){
        //
        PreparedStatement pst = null;
        ResultSet query = null;
        Connection con = null;
        con = DBPool.getConnection();
        String sql = "insert into  user  values(?,?,?,?,?,?)";
        try {
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            int update = basic.update(pst, user.getId(), user.getUsername(), user.getPwd(), user.getRule(), user.getEmail(), user.getQq());
            if ( update ==1){
                con.commit();
                return true;
            }
        }catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            basic.close(pst,con);
        }
        return false;
    }

}
