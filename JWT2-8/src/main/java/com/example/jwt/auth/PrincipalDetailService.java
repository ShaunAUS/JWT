package com.example.jwt.auth;

import com.example.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.jwt.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User userEntity = userRepository.findByUserName(username);

        //UserService에서 userName으로 DB에서 찾은뒤  userDetails타입으로 반환
        return new PrincipalDetails(userEntity);
    }
}
