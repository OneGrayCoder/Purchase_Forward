package com.aki.purchaseforward.service;

import com.aki.purchaseforward.pojo.Good;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:36
 */

public interface GoodService {
    boolean addGood(Good good);

    List<Good> queryGood(Good good);

    List<Good> findAll(int pageNow,int pageSize);
    int getTotalRecord();

    boolean updateGood(Good good);

    boolean deleteGood(int id);
}
