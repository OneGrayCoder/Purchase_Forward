package com.aki.purchaseforward.controller;

import com.aki.purchaseforward.pojo.User;
import com.aki.purchaseforward.service.UserService;
import com.aki.purchaseforward.service.impl.UserServiceImpl;
import com.aki.purchaseforward.util.Tools;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/8 21:05
 */
@WebServlet(value="/qiantai/UserServlet")
//先从注册开始写起
public class UserServlet extends HttpServlet {
    //直接创建userservice对象，方便其他方法调用，避免多次创建对象
    private static String codeChars = "1234567890abcdefghijklmnopqrstuvwxyz";
    private UserService userService = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //所有的关于user请求都交给这个servlet处理JreqType=
        String reqType = request.getParameter("reqType");
        if (reqType.equals("register")){
            register(request,response);
        }else if (reqType.equals("checkName")){
            checkName(request,response);
        }else if (reqType.equals("Login")){
            userLogin(request,response);
        }else if (reqType.equals("securityCodes")){
            securityCode(request,response);
        }


    }

    private void securityCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取
        //获得验证码集合的长度
        int charsLength = codeChars.length();
        //下面3条是关闭客户端浏览器的缓冲区
        response.setHeader("ragma", "No-cache");
        response.setHeader("Cach-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置图形验证码的长宽
        int width = 90, height = 20;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();//获得输出文字的graphics对象
        Random random = new Random();
        g.setColor(getRandomColor(180, 250));//背景颜色
        g.fillRect(0, 0, width, height);
        //设置初始字体
        g.setFont(new Font("Times New Roman",Font.ITALIC,height));
        g.setColor(getRandomColor(120, 180));//字体颜色
        StringBuilder validationCode = new StringBuilder();
        //验证码的随机字体
        String[] fontNames = {"Times New Roman","Book antiqua","Arial"};
        //随机生成3-5个验证码
        for (int i = 0; i < 3+random.nextInt(3); i++) {
            //随机设置当前验证码的字符的字体
            g.setFont(new Font(fontNames[random.nextInt(3)],Font.ITALIC,height));
            //随机获得当前验证码的字符
            char codeChar = codeChars.charAt(random.nextInt(charsLength));
            validationCode.append(codeChar);
            //随机设置当前验证码字符的颜色
            g.setColor(getRandomColor(10, 100));
            //在图形上输出验证码字符，x y随机生成
            g.drawString(String.valueOf(codeChar), 16*i+random.nextInt(7), height-random.nextInt(6));
        }
        //获得session对象
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(5*60);
        //将验证码保存在session对象中，key为validation_code
        session.setAttribute("validation_code", validationCode.toString());
        System.out.println("本次验证码是:"+validationCode.toString());
        g.dispose();
        OutputStream os = response.getOutputStream();
        ImageIO.write(image,"JPEG",os);//以JPEG格式向客户端发送图形验证码

    }

    //获取随机颜色
    private static Color getRandomColor(int minColor, int maxColor){
        Random random = new Random();
        if(minColor>255)
            minColor = 255;
        if(maxColor>255)
            maxColor = 255;
        int red = minColor + random.nextInt(maxColor - minColor);
        int green = minColor + random.nextInt(maxColor - minColor);
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red,green,blue);
    }


    private void userLogin(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        System.out.println("LOGIN START");
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        String s_Password = Tools.md5(pwd);
        //获取用户输入的验证码
        String securityCdoe = request.getParameter("securityCoder");//?没渠道
        //要判断验证码是否一致
        HttpSession session1 = request.getSession();
       String code = (String) session1.getAttribute("validation_code");
        System.out.println("session中获取到的验证码: "+code);
        //忽略大小写比较
        String loginInfo = "";
        if (code == null ||code.equalsIgnoreCase(securityCdoe)){
            //说明验证码没有错误
            //查询是否有这样的用户
            User user = new User();
            user.setUsername(username);
            user.setPwd(s_Password);
            boolean b = userService.checkLogin(user);
            if (b){
                //直接跳转到主页面
                session1.setAttribute("username",username);
                response.sendRedirect("main.jsp");
            }else {
                loginInfo = "登录失败";
                session1.setAttribute("loginInfo",loginInfo);
                request.getRequestDispatcher("login.jsp").forward(request,response);

            }
        }else {
            //验证码有错误，停留在登录姐买你
            loginInfo = "验证码错误";
            session1.setAttribute("loginInfo",loginInfo);
            request.getRequestDispatcher("login.jsp").forward(request,response);


        }
    }

    private void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //检查用户名是否重复
        System.out.println("此方法执行了");
        String username = request.getParameter("username");
        boolean b = userService.checkName(username);
        System.out.println(b);
        //问题是怎么把数据返回
        response.getWriter().print(b);
        response.getWriter().flush();


    }

    private void register(HttpServletRequest request,HttpServletResponse response){
        //此处不考虑为空的情况,由前端验证
        System.out.println("REGISTRE START");
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        //得到密码之后进行加密
        String password = request.getParameter("pwd").trim();
        //为加密用户密码设置一个方法
        String securityPassword = userService.securityPass(password);
        String qq = request.getParameter("qq").trim();
        String registInfo = "";
        //默认获取到的都是非空的
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPwd(securityPassword);
        System.out.println("加密过后的密码是:"+securityPassword);
        user.setQq(qq);
        //0为普通用户,1为管理员用户
        user.setRule(0);
        System.out.println();
        boolean result = userService.regist(user);
        if (result){
            //注册成功,把用户存放到session中
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(60*60);
            try {
                response.sendRedirect("main.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {//注册失败
            registInfo = "注册失败";
            request.setAttribute("registInfo",registInfo);
            try {
                request.getRequestDispatcher("register.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}