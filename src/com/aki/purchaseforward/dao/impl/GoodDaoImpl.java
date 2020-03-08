package com.aki.purchaseforward.dao.impl;

import com.aki.purchaseforward.dao.GoodDao;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.util.DBPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:40
 */
public class GoodDaoImpl implements GoodDao {
    private BasicDaoUtil basic = new BasicDaoUtil();
    @Override
    public boolean addGood(Good good) {
        //实现逻辑
        //向表中插入数据
        Connection con = null;
        PreparedStatement pre = null;
        con = DBPool.getConnection();
        try {
            con.setAutoCommit(false);
            String sql = "insert into good values(null,?,?,?,?)";
            pre = con.prepareStatement(sql);
            int update = basic.update(pre, good.getGoodname(), good.getGoodtype(), good.getPrice(), good.getPic());
            if (update!=0&& update==1){
                System.out.println(update);
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            basic.close(pre,con);
        }
        return false;
    }


    //查询信息,返回list集合
    @Override
    public List<Good> queryGood(Good good){
        List<Good> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        //取出数据,进行查询
        con = DBPool.getConnection();
        ResultSet query = null;
        //先要做个判断,这几个值都不能为空串。
        String s = "select id,goodname,goodtype,price from good where ";
        //条件拼接
        String condition = "";
        try {
            //说明用户填了数字
            if (good.getId() != -1){
                condition += "id="+good.getId();
            }
            if (!good.getGoodname().isEmpty()){
                if (condition.isEmpty()){
                    condition +=" goodname like '%"+good.getGoodname()+"%'";
                }else{
                    condition +=" and goodname like '%"+good.getGoodname()+"%'";
                }
            }
            if (!good.getGoodtype().isEmpty()){
                if (condition.isEmpty()){
                    condition +=" goodtype like '%"+good.getGoodtype()+"%'";
                }else{
                    condition +=" and goodtype like '%"+good.getGoodtype()+"%' ";
                }
            }
            String sql = s + condition;
            System.out.println(sql);
            pst = con.prepareStatement(sql);
            query = pst.executeQuery();
            while(query!=null&&query.next()){
                Good good1 = new Good();
                int id = query.getInt("id");
                good1.setId(query.getInt("id"));
                System.out.println("id"+id);
                good1.setGoodname(query.getString(2));
                good1.setGoodtype(query.getString(3));
                good1.setPrice(query.getDouble(4));
//                System.out.println("good1的id是"+good1.getId());
                list.add(good1);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(query,pst,con);
        }
        return null;
    }

    @Override
    public List<Good> findAllGoods(int pageNow,int pageSize){
        //根据pagenow查询商品信息
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet result = null;
        con = DBPool.getConnection();
        //从第一页开始,每页取10条信息
        try {
                String sql = "select * from good order by id limit ?,?";
                pst = con.prepareStatement(sql);
                result = basic.query(pst, (pageNow -1)* pageSize,pageSize);

            List<Good> list = new ArrayList<>();
            while (result!=null&&result.next()){
                Good goodAll = new Good();
                goodAll.setId(result.getInt(1));
                goodAll.setGoodname(result.getString(2));
                goodAll.setGoodtype(result.getString(3));
                goodAll.setPrice(result.getDouble(4));
                list.add(goodAll);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(result,pst,con);
        }

        return null;
    }


    @Override
    public int getTotalRecord(){
        //获得总记录数
        int record = 0;
        Connection con = null;
        PreparedStatement pst = null;
        con = DBPool.getConnection();
        ResultSet set = null;
        String sql = "select count(id) from good";
        try {
            pst = con.prepareStatement(sql);
            set = basic.query(pst);
            //获取第一条记录
            if (set != null&&set.next()){
                record = set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            basic.close(set,pst,con);
        }
        return record;
    }


    public boolean updateGoods(Good good){
        //update good
        Connection con = null;
        PreparedStatement pst = null;
        try {
            String sql = "update good set  goodname= ? , goodtype = ? , price = ? where id = ?";
            con = DBPool.getConnection();
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            int update = basic.update(pst, good.getGoodname(), good.getGoodtype(), good.getPrice(),good.getId());
            if (update == 1){
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            basic.close(pst,con);
        }
        return false;
    }

    @Override
    public boolean delete(int id){
        PreparedStatement pst = null;
        Connection con = null;
        String sql = "delete from good where id = ?";
        try {
            con = DBPool.getConnection();
            con.setAutoCommit(false);
            pst = con.prepareStatement(sql);
            int update = basic.update(pst, id);
            if (update == 1){
                con.commit();
                return true;
            }
        } catch (SQLException e) {
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
