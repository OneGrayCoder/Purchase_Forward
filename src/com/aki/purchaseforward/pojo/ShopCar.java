package com.aki.purchaseforward.pojo;

import com.aki.purchaseforward.service.GoodService;
import com.aki.purchaseforward.service.impl.GoodServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/11 22:16
 */
//这个类用来封装购物车中的数据
public class ShopCar {
    //包含对购物车的一系列操作
    //因为每一个用户只有一个购物车,每一个购物车对应一个iD,是键值对的形式。
    // 所以
    //对于购物车，我们可以将购物车存放于session中
    private Map<Integer,Integer> map;

    public void setMap(Map<Integer,Integer> map){
        this.map = map;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }
    //针对这个购物车有一系列方法:
    //添加购物车，当用户点击图片或者点击超链接,即视为添加购物车
    //为此,我们需要知道此商品的ID,初始值为一
    //用户可以在购物车里修改数量。
    public void add(Integer id){
        //
        if (map ==null){
            map = new HashMap<>();
        }   //如果不为空就创建出来
        map.put(id,1);
    }

    //清空
    public void cleanUp(){
        //不为空,既可以清空购物车
        if (map != null){
            map.clear();
        }
    }

    //删除购物车
    //根据ID清除当前对象
    public void delete(Integer id){
        map.remove(id);
    }

    //更新购物车
    public void update(Integer[] ids,Integer[] counts){
        for (int i = 0; i <ids.length ; i++) {
            //重新取值,然后重新赋值
            //因为ID和count是一一对应的,所以不会错位
            map.put(ids[i],counts[i]);
        }
    }


    //得到商品总价,需要获得所有的id和数量
    public Map<Integer,Double> getTotalPrice(){
        Map<Integer,Double> mm = new HashMap<>();
        GoodService goodService = new GoodServiceImpl();
        List<Good> goodList = goodService.queryGoodByCar(this);
        int count = 0;
        double totalAmount = 0;
        //遍历
        if (goodList != null){
            for (Good good:goodList) {
                System.out.println(good.getId());
                count++;
                totalAmount += good.getPrice()*good.getCount();
            }
        }
        System.out.println(count);
        System.out.println(totalAmount);
        mm.put(count,totalAmount);
        return mm;
    }
}
