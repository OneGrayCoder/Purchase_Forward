package com.aki.purchaseforward.dao.impl;

import com.aki.purchaseforward.dao.UserDao;
import com.aki.purchaseforward.util.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 17:52
 */

//数据库操作分为两大部分,查询和增删改
//然后公共的关闭资源的方法。
public class BasicDaoUtil  {
private static BasicDaoUtil bas = new BasicDaoUtil();
    //将预编译中的设置值的操作封装起来,方便使用
    //此方法执行查询操作

    public  ResultSet query(PreparedStatement pre, Object ...obj) throws SQLException {
        ResultSet rs = null;
        //根据问号设置每个值
            if (obj == null || obj.length==0){
                rs = pre.executeQuery();
            }else{
                for (int i =0;i<obj.length;i++){
                    pre.setObject(i+1,obj[i]);
                }
                rs = pre.executeQuery();
            }
        return rs;
    }

    //增删改
    //返回受影响的行数
    public  int update(PreparedStatement pre, Object ...obj) throws SQLException{
        //根据问号设置每个值
             int i = 0;
             if (obj != null){
                 for (int j =0;j<obj.length;j++){
                     pre.setObject(j+1,obj[j]);
                 }
             }

            i = pre.executeUpdate();
             return i;
    }


    public  void close(ResultSet rs, PreparedStatement pre,Connection con){
        try {
            if (rs!=null){
                rs.close();
            }
            if (pre != null){
                pre.close();
            }
            //注意,此处不是关闭,向连接池中归还连接
            if (con != null){
                con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //针对，增删改类语句
    public  void close( PreparedStatement pre,Connection con){
        close(null,pre,con);
    }

    //测试成功
    //开始做逻辑



}
