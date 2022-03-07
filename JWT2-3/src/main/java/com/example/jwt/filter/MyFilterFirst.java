package com.example.jwt.filter;


import javax.servlet.*;
import java.io.IOException;

public class MyFilterFirst implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터1");
        chain.doFilter(request,response);  //필터체인에다가 다시 등록
    }
}
