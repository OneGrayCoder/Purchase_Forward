package com.aki.purchaseforward.dao.impl;

import com.aki.purchaseforward.dao.UserDao;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.User;
import com.aki.purchaseforward.util.DBPool;
import com.aki.purchaseforward.util.Tools;

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
    public boolean regist(User user){
        boolean b = false;
        Connection con = null;
        PreparedStatement pst = null;
        String sql = "insert into user values(null,?,?,?,?,?)";
        con = DBPool.getConnection();
        try {
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            int i = basic.update(pst, user.getUsername(), user.getPwd(), user.getRule(), user.getEmail(), user.getQq());
            if (i==1){
                con.commit();
                b = true;
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            basic.close(pst,con);
        }
        return b;
    }

    @Override
    public String securityPassword(String password) {
        return Tools.md5(password);
    }

    @Override
    public boolean checkName(String username){
        boolean res = false;
        int count = 0;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet query = null;
        String sql = "select count(*) from user where username =? and rule = 0 ";
        con = DBPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            query = basic.query(pst, username);
            while(query != null&&query.next()){
                count = query.getInt(1);
            }
            System.out.println(count);
            if (count ==1){
                res = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(query,pst,con);
        }
        return res;
    }

    @Override
    public  boolean User_Login(User user){
        boolean check = false;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet query = null;
        String sql = "select count(*) from user where username= ? and pwd =?";
        con = DBPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            query = basic.query(pst, user.getUsername(), user.getPwd());
            if (query!=null&&query.next()){
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(query,pst,con);
        }
        return check;
    }




}
