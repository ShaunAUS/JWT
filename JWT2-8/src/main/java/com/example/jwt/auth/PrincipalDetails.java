package com.example.jwt.auth;

import com.example.jwt.entity.User;
import lombok.Data;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//User 클래스에는 id ,pass 만있기때문에 추가 정보 추가하려면 UserDetails 구현해야함
//userDetails 타입으로 반환뒤 userNamePasswordAuthenticaitoinToken에 넣어준다(계정,비번,권한) -> SecurityContextHolder -> Session
@Data
public class PrincipalDetails implements UserDetails {


    private User user;

    //DB에서 userName으로 찾은 계정이 들어옴
    public PrincipalDetails(User user){
        this.user = user;
    }


    //user에 있는 권한을 arrayList에 담기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRolesList().forEach(r->{
            authorities.add(()-> r);
        });
        return authorities;
    }

    @Override
    public String getPassword()  {
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
