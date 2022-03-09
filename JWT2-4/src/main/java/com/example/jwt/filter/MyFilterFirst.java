package com.example.jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


// 처음에 토큰으로 인증을 하는 과정이라 SpringSecurity보다 앞에 작동해야함
public class MyFilterFirst implements Filter {


    // 클라가 id,password 정상적으로 로그인 하면 토큰을 준다.
    // 그 이후 요청이 올때마다 header 안에 Authorization 안의 value값으로 토큰을 가져온다. 그 토큰을 검증만 하면된다
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest reg = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        //헤더 값에 xxx라는 토큰값이 있으면 필터체인을 타도록 한다.
        if(reg.getMethod().equals(("POST"))) {

            System.out.println("POST요청 됨");
            String header = reg.getHeader("Authorization");
            System.out.println(header);
            System.out.println("필터1");

            if (header.equals("xxx")) {

                //필터체인에다가 다시 등록
                chain.doFilter(request, response);

            } else {
                System.out.println("인증 안됨");
            }
        }


    }
}
