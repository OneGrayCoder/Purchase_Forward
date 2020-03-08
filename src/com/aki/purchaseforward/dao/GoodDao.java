package com.aki.purchaseforward.dao;

import com.aki.purchaseforward.pojo.Good;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:39
 */
public interface GoodDao {
    boolean addGood(Good good);


    //查询信息
    List<Good> queryGood(Good good);


    List<Good> findAllGoods(int pageNow,int pageSize);

    int getTotalRecord();

    boolean updateGoods(Good good);

    boolean delete(int id);


}
