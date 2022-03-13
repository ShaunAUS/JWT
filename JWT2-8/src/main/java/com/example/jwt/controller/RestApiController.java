package com.example.jwt.controller;


import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final PasswordEncoder passwordEncoder;       위에랑 뭔차이지??


    @GetMapping("/home")
    public String home(){

        return "<h1>home</h1>";
    }


    @PostMapping("join")
    public String join(@RequestBody User user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입 완료";
    }



    @GetMapping("/api/v1/user")
    public String user(){

        return "user";
    }

    @GetMapping("/api/v1/manager")
    public String manager(){

        return "user";
    }

    @GetMapping("/api/v1/admin")
    public String admin(){

        return "user";
    }



}

