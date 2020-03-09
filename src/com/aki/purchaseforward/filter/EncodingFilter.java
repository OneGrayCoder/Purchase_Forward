package com.aki.purchaseforward.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @AUTUOR QXW
 * @CREATE 2020/2/29 19:39
 */
@WebFilter(value = "/*",initParams = {@WebInitParam(name="encode",value = "UTF-8")})
public class EncodingFilter implements Filter {
    private String encode;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding(encode);
        response.setCharacterEncoding(encode);
        response.setContentType("text/html;charset="+encode);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter Start");
        encode = config.getInitParameter("encode");

    }

}
