package com.aki.purchaseforward.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 13:33
 */

//对方问后台的
public class MainPageAccessFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //对于session中没有用户信息的不让其跳到主页面
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //获取session
        HttpSession session = request.getSession(false);
        if (session == null){
            response.sendRedirect("login.jsp");
        }else{
            Object user = session.getAttribute("user");
            //contextpath代表当前应用,注意,是我们发布到tomcat中的应用名
//        System.out.println(request.getContextPath());
            if (user != null){
                //user不为空,可以放行
                chain.doFilter(request, response);
            }else {
                //回到登录页面
                response.sendRedirect("login.jsp");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
