package com.example.jwt.jwtt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.auth.PrincipalDetails;
import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//스프링 시큐리가 가지고 있는 필터중에 BasiceAuthenticationFilter 라는것이 있다
//권한이나 인증 필요하면 이 필터를 무조건 타게 되있음  // 서버에서 토큰 유효성 검사
//권한이나 인증 안필요하면 이 필터 안탐
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository=userRepository;
    }

    //여기서 인증이나 권한 요청이 필요하면 이 필터를 탄다
    //JWT 토큰 유효성 검사
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        //헤더 안에 Authorization열어서 클라가 보낸 토큰값 꺼내기
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);



        //Header가 있는지 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){

            chain.doFilter(request,response);
            return;

        }

        //JWT토큰을 검증해서 정상적인 사용자인지 체크
        //순수 토큰값만 추출
        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");

        //클라가 보낸 JWT토큰을 secretKey로 시그니처 확인
        String userName = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken)   //verify() = Decode
                .getClaim("username").asString();



        if(userName != null){

            //토큰에서 가져온값으로 계정 가져와서
            User user = userRepository.findByUserName(userName);

            //userName으로 인증된 객체 추출
            PrincipalDetails principalDetails = new PrincipalDetails(user);


            //userDetails 타입 계정을 토큰에 넣고 그걸 Authentication 객체에 넣기 - > SecurityContextHoleder -> Session
            //계정, 비밀번호, 권한
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

            //강제로 세션에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);

        }




    }
}

