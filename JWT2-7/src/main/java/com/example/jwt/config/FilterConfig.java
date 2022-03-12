package com.example.jwt.config;


import com.example.jwt.filter.MyFilterFirst;
import com.example.jwt.filter.MyFilterSeond;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//시큐리티 필터 체인에 등록하는게 아니라 직접 등록
//필터는 따로만들고 이건 등록만하는것
//SecurityConfig 에 등록한 필터 (스프링시큐리티 필터체인) 이 우리가 만든것보다 무조건 먼저 실행된다
@Configuration
public class FilterConfig {



    //첫번쨰 필터
    @Bean
    public FilterRegistrationBean<MyFilterFirst> filter(){
        FilterRegistrationBean<MyFilterFirst> bean =new FilterRegistrationBean<MyFilterFirst>();
        bean.addUrlPatterns("/*"); //모든 요청에 대한 응답
        bean.setOrder(0);  //낮은 번호가 가장 먼저 실행

        return bean;
    }

    //두번째 필터
    @Bean
    public FilterRegistrationBean<MyFilterSeond> filterSecond(){
        FilterRegistrationBean<MyFilterSeond> bean =new FilterRegistrationBean<MyFilterSeond>();
        bean.addUrlPatterns("/*"); //모든 요청에 대한 응답
        bean.setOrder(0);  //낮은 번호가 가장 먼저 실행

        return bean;
    }

}
