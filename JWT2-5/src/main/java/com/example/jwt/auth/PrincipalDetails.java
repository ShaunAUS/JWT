package com.example.jwt.auth;

import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//User 클래스에는 id ,pass 만있기때문에 추가 정보 추가하려면 UserDetails 구현해야함
public class PrincipalDetails implements UserDetails {

    //UserDetail인터페이스를 구현해놓은게 User클래스
    private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }


    //user에 있는 권한을 arrayList에 담기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(r->{
            authorities.add(()-> String.valueOf(r));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
