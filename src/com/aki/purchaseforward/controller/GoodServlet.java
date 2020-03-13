package com.aki.purchaseforward.controller;

import com.aki.purchaseforward.pojo.Good;
import com.aki.purchaseforward.pojo.ShopCar;
import com.aki.purchaseforward.service.GoodService;
import com.aki.purchaseforward.service.impl.GoodServiceImpl;
import com.aki.purchaseforward.util.DBPool;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/11 16:54
 */
@WebServlet(value= "/qiantai/GoodSvl")
public class GoodServlet extends HttpServlet {
    private GoodService goodService = new GoodServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    //查询所有商品类型
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reqType = request.getParameter("reqType");
        if (reqType.equals("main")){
            queryGoodType(request,response);
        }else if (reqType.equals("img")){
            downImg(request,response);
        }else if (reqType.equals("addCar")){
            addShoppingCar(request,response);
        }else if (reqType.equals("delete")){
            deleteCar(request,response);
        }else if (reqType.equals("clearAll")){
            clearAllCar(request,response);
        }else if (reqType.equals("update")){
            updateCar(request,response);
        }else if (reqType.equals("flow")){
            lookForFlow(request,response);
        }else if(reqType.equals("caculator")){
            caculator(request,response);
        }
    }

    private void caculator(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //此方法用来结算
        HttpSession session = request.getSession();
        ShopCar car = (ShopCar) session.getAttribute("car");
        if (car != null){
            int count = 0;
            double amount = 0;
            Map<Integer, Double> totalPrice = car.getTotalPrice();
            Set<Integer> integers = totalPrice.keySet();
            for (Integer s:integers) {
                count = s;
                amount = totalPrice.get(s);
            }
            request.setAttribute("count",count);
            request.setAttribute("amount",amount);
            response.getWriter().print("<script>window.alert('支付成功总金额为:"+ amount+"')</script>");
        }else{
            response.getWriter().print("<script>window.alert('您的购物车没有商品!')</script>");
        }



    }

    private void lookForFlow(HttpServletRequest request, HttpServletResponse response) {
        //相当于到购物车页面(),并且我们需要查询
        //最终目的是重定向到购物车页面,如果有数据,就要查出来,如果没有数据
        //也要到购物车页面
        HttpSession session = request.getSession();
         ShopCar car = (ShopCar) session.getAttribute("car");

         if (car != null){
             int count = 0;
             double amount = 0;
                 Map<Integer, Double> totalPrice = car.getTotalPrice();
                 Set<Integer> integers = totalPrice.keySet();
                 for (Integer s:integers) {
                     count = s;
                     amount = totalPrice.get(s);
             }
             request.setAttribute("count",count);
             request.setAttribute("amount",amount);
             queryByCar(request,response,car);
         }else{
             try {
                 response.sendRedirect("flow.jsp");
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }

    //当用户更改数量时,点击更新按钮,或者离开,我们需要对数量和小计,以及总价格进行更新。
    private void updateCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("UPDATE START");
        //获得多个值
        String[] ids = request.getParameterValues("ids");
        String[] counts = request.getParameterValues("counts");
        Integer[] id = null;
        Integer[] count = null;
        if (ids == null || counts == null){
            //如果为空,就示意购物车没有商品
            response.getWriter().print("<script>alert('已经没有商品');window.history.back()</script>");
        }else{
            id = new Integer[ids.length];
            count = new Integer[counts.length];
            for (int i=0;i<ids.length;i++){
                id[i] = Integer.parseInt(ids[i]);
            }
            for (int i=0;i<counts.length;i++){
                count[i] = Integer.parseInt(counts[i]);
            }
            HttpSession session = request.getSession();
            ShopCar car = (ShopCar)session.getAttribute("car");
            if (car != null){
                car.update(id,count);
            }
            System.out.println(Arrays.toString(id));
            System.out.println(Arrays.toString(count));
            session.setAttribute("car",car);
            queryByCar(request,response,car);
            //先要更新,用户只可能更改产品数量,获得所有的产品编号与铲平数量
        }

    }

    //清空购物车
    private void clearAllCar(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("ClearAll START");
        HttpSession session = request.getSession();
        ShopCar car = (ShopCar) session.getAttribute("car");
        car.cleanUp();//清空购物车
        queryByCar(request,response,car);
    }

    //删除购物车中的某一项商品
    private void deleteCar(HttpServletRequest request,HttpServletResponse response){
        System.out.println("DELETECAR START");
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
         ShopCar car = (ShopCar) session.getAttribute("car");
         car.delete(id);
         //获得map中已经存在的
        //再次把删除后的car存入session中
        session.setAttribute("car",car);
        //再次调用根据id查找的方法
        queryByCar(request, response, car);
    }

    //将重新查询的方法抽取出来，减少重复操作
    private void queryByCar(HttpServletRequest request, HttpServletResponse response, ShopCar car) {
        int count = 0;
        double amount = 0;
        if (car != null){
            Map<Integer, Double> totalPrice = car.getTotalPrice();
            Set<Integer> integers = totalPrice.keySet();
            for (Integer s:integers) {
                count = s;
                amount = totalPrice.get(s);
            }
        }
        request.setAttribute("count",count);
        request.setAttribute("amount",amount);
        List<Good> queryGoodByCar = goodService.queryGoodByCar(car);
        for (Good good:queryGoodByCar) {
            //打印一下所有的id
            System.out.println(good.getId());
        }
        request.setAttribute("queryGoodByCar",queryGoodByCar);
        try {
            request.getRequestDispatcher("flow.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addShoppingCar(HttpServletRequest request,HttpServletResponse response){
        //先获取此商品的id
        Integer id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
         ShopCar car = (ShopCar) session.getAttribute("car");
         if (car == null) {
             //判断session中有没有购物车对象，如果有就创建出来
             car = new ShopCar();
         }
         car.add(id);
         session.setAttribute("car",car);
         session.setMaxInactiveInterval(60*10);
        int count = 0;
        double amount = 0;
        if (car != null){
            Map<Integer, Double> totalPrice = car.getTotalPrice();
            Set<Integer> integers = totalPrice.keySet();
            for (Integer s:integers) {
                count = s;
                amount = totalPrice.get(s);
            }
        }
        request.setAttribute("count",count);
        request.setAttribute("amount",amount);

         //然后把car对象传递进去,根据car中的id来得到存储good对象(可能有多个)的list集合
             List<Good> queryGoodByCar = goodService.queryGoodByCar(car);
        for (Good good:queryGoodByCar) {
            System.out.println(good.getPrice());
        }
             //把东西都存放到session中
             request.setAttribute("queryGoodByCar",queryGoodByCar);
             try {
                 request.getRequestDispatcher("flow.jsp").forward(request,response);
             } catch (ServletException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             //说明购物车中有对象,
             //然后调用ShopCar的方法
             //问题,怎么取出其他的信息


        //再根据id查询出商品信息


    }

    private void downImg(HttpServletRequest request, HttpServletResponse response) {
        //此方法直接下载文件
        //因为文件存放在安全目录下,所以不能直接访问
        //首先获得文件的真实路径
        //利用文件读取流和响应对象的输出流
        //响应到客户端,因为是图片请求，所以我们响应到图片上
        String filename = request.getParameter("pic");
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/"+filename);
        FileInputStream fis = null;
        ServletOutputStream outputStream = null;
        try {
            fis = new FileInputStream(new File(path));
            byte[] b = new byte[1024];
            int len = -1;
             outputStream = response.getOutputStream();
            while((len =fis.read(b))!=-1){
                outputStream.write(b,0,len);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //先关闭读取流，再关闭输入流。
                fis.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //得到所有去重复商品类型
    private void queryGoodType(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("你好");
        HttpSession session = request.getSession();
        ShopCar car = (ShopCar) session.getAttribute("car");
        //无论car是否为空,都去查找
        int count = 0;
        double amount = 0;
        if (car != null){
            Map<Integer, Double> totalPrice = car.getTotalPrice();
            Set<Integer> integers = totalPrice.keySet();
            for (Integer s:integers) {
                count = s;
                amount = totalPrice.get(s);
            }
        }
        request.setAttribute("count",count);
        request.setAttribute("amount",amount);
        List<String> list = goodService.queryGoodType();
        String goodType = request.getParameter("goodtype");
        if (list.size()>0&&goodType == null){
            //取出第一个类别,如果为空,则默认取第一个，
            goodType = list.get(0);
        }
        //根据商品类型封装所有的商品数据,因为要展示数据
        //首页面默认展示第一个商品类别的所有商品
        //得到所有
        List<Good> goodList = goodService.queryGood(goodType);
        request.setAttribute("list",list);
        if (goodList.size()>0){
            request.setAttribute("goodList",goodList);
        }
            try {
                request.getRequestDispatcher("main.jsp").forward(request,response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
    }


}
