package com.aki.purchaseforward.service.impl;

import com.aki.purchaseforward.dao.GoodDao;
import com.aki.purchaseforward.dao.impl.GoodDaoImpl;
import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.ShopCar;
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
    public List<String> queryGoodType(){

        return goodDao.queryAllType();
    }

    @Override
    public List<Good> queryGood(String goodType){
        return goodDao.queryGoodByType(goodType);
    }

    @Override
    public List<Good> queryGoodByCar(ShopCar car){
        return goodDao.queryGoodByCar(car);
    }

}
