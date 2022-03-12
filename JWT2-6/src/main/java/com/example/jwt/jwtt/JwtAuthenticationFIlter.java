package com.example.jwt.jwtt;

import com.example.jwt.auth.PrincipalDetails;
import com.example.jwt.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//formLogin -> disable 상태라 UsernamePasswordAuthenticationFilter가 동작 안함
// 동작 시키려면 SecurityConfig 에 넣어줘야함 // 이 클래스는 필터를 상속받은 간접 클래스라  formLogin disable 영향 x
@RequiredArgsConstructor
public class JwtAuthenticationFIlter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //login 요청을 하면 로그인 시도를 위해 실행되는 메서드
    //1. AuthenticationManager 저로 로그인 시도 -> PrincipalDetailService () -> PrincipalDetails 를 세션에 담는다
   @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


       try {

           //JSON으로 들어온 데이터를 파싱해준다
           ObjectMapper om = new ObjectMapper();
           //JSON으로 들어온 데이터를 USER 클래스에 파싱 해준다
           User user = om.readValue(request.getInputStream(), User.class);

           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                   = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

           //UserDetailService 의 loadUserByName 실행
           Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


           PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();


           //authentication 은 session에 저장
           return authentication;

       } catch (IOException e) {
           e.printStackTrace();
       }


       return null;
    }


    //위에 메서드 인증 처리가 정상적으로 되면 실행되는 메서드
    //여기서 JWT토큰 생성해서 클라한테 주기
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
