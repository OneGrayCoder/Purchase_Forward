package com.aki.purchaseforward.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 13:15
 */

//负责加载配置文件
//封装一些我们常用的操作
//这个类就作为我们的工具类
public class DBPool {
    public static ComboPooledDataSource ds = new ComboPooledDataSource();
    public static Connection connection = null;


    //使用静态代码块加载配置文件
    //获取数据源
    //使用properties集合
    static{
        Properties pro = new Properties();
        ClassLoader classLoader = DBPool.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream("c3p0.properties");
        try {
            //加载，然后我们通过properties取值
            pro.load(resource);
            //设置配置文件中的值
            ds.setJdbcUrl(pro.getProperty("url"));
            ds.setUser(pro.getProperty("user"));
            ds.setPassword(pro.getProperty("password"));
            ds.setDriverClass(pro.getProperty("driverClassName"));
            ds.setCheckoutTimeout(Integer.parseInt(pro.getProperty("checkoutTimeout")));
            ds.setIdleConnectionTestPeriod(Integer.parseInt(pro.getProperty("idleConnectionTestPeriod")));
            ds.setInitialPoolSize(Integer.parseInt(pro.getProperty("initialPoolSize")));
            ds.setMaxIdleTime(Integer.parseInt(pro.getProperty("maxIdleTime")));
            ds.setMaxPoolSize(Integer.parseInt(pro.getProperty("maxPoolSize")));
            ds.setMinPoolSize(Integer.parseInt(pro.getProperty("minPoolSize")));
            ds.setMaxStatements(Integer.parseInt(pro.getProperty("maxStatements")));
            ds.setAcquireIncrement(Integer.parseInt(pro.getProperty("AcquireIncrement")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }


    //此方法返回连接池连接
    public static Connection getConnection(){
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //针对查询


    //返回database
    public static DataSource getDs(){
        return ds;
    }


}
