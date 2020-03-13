package com.aki.purchaseforward.service;

import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.ShopCar;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:36
 */

public interface GoodService {
    List<String > queryGoodType();

    List<Good> queryGood(String goodType);

    //根据ID查询商品信息
    List<Good> queryGoodByCar(ShopCar car);
}
