package com.aki.purchaseforward.dao.impl;

import com.aki.purchaseforward.dao.GoodDao;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.ShopCar;
import com.aki.purchaseforward.util.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:40
 */
public class GoodDaoImpl implements GoodDao {
    private BasicDaoUtil basic = new BasicDaoUtil();
    @Override
    public List<String> queryAllType(){
        List<String> goodType = new ArrayList<>();
        String sql = "select distinct goodtype from good";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        con = DBPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            resultSet = basic.query(pst,null);
            while(resultSet != null&&resultSet.next()){
                String string = resultSet.getString(1);
                goodType.add(string);
            }
            System.out.println("当前集合中有:"+goodType.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(resultSet,pst,con);
        }
        return goodType;
    }

    //根据商品类型查询商品，初始显示第一个商品类型

    @Override
    public List<Good> queryGoodByType(String goodType){
        List<Good> goodList = new ArrayList<>();
        String sql = "select *from good where goodtype = ? order by id limit ?,?";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        con = DBPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            resultSet = basic.query(pst, goodType, 0, 20);
            while(resultSet!=null&&resultSet.next()){
                Good good = new Good();
                good.setId(resultSet.getInt(1));
                good.setGoodname(resultSet.getString(2));
                good.setGoodtype(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                good.setPic(resultSet.getString(5));
                //设置数量

                goodList.add(good);
            }
            System.out.println("当前集合中有: "+goodList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(resultSet,pst,con);
        }
        return goodList;
    }

    @Override
    public List<Good> queryGoodByCar(ShopCar car) {
        List<Good> list = new ArrayList<>();
        //因为car中存放的是map集合,因此我们要获得其中所有的id
        //用模糊查询来获得和这些id相关的信息
        Map<Integer, Integer> map = car.getMap();
        //用可变字符串,便于拼接
        StringBuilder sb = new StringBuilder();
        Set<Integer> integers = map.keySet();
        //拼接模糊查询的字符串
        String substring = "";
        for (int s:integers) {
            sb.append(s).append(",");
        }
        System.out.println(sb.toString());
        String idS = sb.toString();
        if (!idS.isEmpty()){
            //如果不为空,说明有去掉最后的逗号
            System.out.println(idS.length());//length是2
            substring = idS.substring(0, idS.length()-1);
        }else {//如果为空,直接返回
            return list;
        }

        String sql = "select *from good where id in ("+substring+")";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet resultSet = null;
        con = DBPool.getConnection();
        try {
            pst = con.prepareStatement(sql);
            resultSet = pst.executeQuery();
            while(resultSet!=null&&resultSet.next()){
                Good good = new Good();
                good.setId(resultSet.getInt(1));
                good.setGoodname(resultSet.getString(2));
                good.setGoodtype(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                //设置数量，根据id来取出来，id为从good中根据id查询出来的来取
                good.setCount(map.get(resultSet.getInt(1)));
                list.add(good);
            }
            System.out.println("当前集合中有: "+list.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(resultSet,pst,con);
        }
        return list;
    }


}
