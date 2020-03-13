package com.aki.purchaseforward.dao;

import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.ShopCar;

import java.util.List;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 23:39
 */

//这是前台项目的gooddao
public interface GoodDao {

   List<String> queryAllType();

   List<Good> queryGoodByType(String goodType);


   List<Good> queryGoodByCar(ShopCar car);


}
