package com.artbrain.security;


import com.artbrain.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.Collection;

/**
 * Created by hongyu on 2017/4/7.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        System.out.println("--MyAuProv");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername(username);
        if (user == null) {
            System.out.println("Username not found.");
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        String hashPassword = user.getPassword();
        String salt = user.getSalt();
        if (user.getIsDel() == 1) {
            System.out.println("用户已逻辑删除.code1.id:" + user.getId());
            throw new BadCredentialsException("用户已逻辑删除.code2.id:" + user.getId());
        }
        if (user.getIsStop() == 1 ){
            System.out.println("用户已停用.code1.id:" + user.getId());
            throw new BadCredentialsException("用户已停用.code1.id:" + user.getId());
        }
        System.out.println(user.getIsStop()+","+user.getIsDel());
        if (!CryptoUtils.verify(hashPassword, password, salt)) {
            System.out.println("密码不匹配.code1.id:" + user.getId());
            throw new BadCredentialsException("密码不匹配.code3.id:" + user.getId());
        }
        System.out.println("login success");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        System.out.println("1: " + authorities.toString());
        System.out.println("2: " + new UsernamePasswordAuthenticationToken(user, password, authorities).toString());
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
