package com.aki.purchaseforward.service.impl;

import com.aki.purchaseforward.dao.GoodDao;
import com.aki.purchaseforward.dao.impl.GoodDaoImpl;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.service.GoodService;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:37
 */
public class GoodServiceImpl  implements GoodService {
    //多态
    private GoodDao goodDao = new GoodDaoImpl();

    @Override
    public boolean addGood(Good good) {
        return goodDao.addGood(good);
    }

    @Override
    public List<Good> queryGood(Good good){
        return goodDao.queryGood(good);
    }

    @Override
    public List<Good> findAll(int pageNow,int pageSize){
        return goodDao.findAllGoods(pageNow,pageSize);
    }

    @Override
    public int getTotalRecord(){
        return goodDao.getTotalRecord();
    }

    @Override
    public boolean updateGood(Good good){
        return goodDao.updateGoods(good);
    }


    @Override
    public boolean deleteGood(int id){
        return goodDao.delete(id);
    }
}
