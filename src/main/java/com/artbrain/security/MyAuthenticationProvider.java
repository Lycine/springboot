package com.artbrain.security;


import com.artbrain.util.CryptoUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import java.util.Collection;

import static com.artbrain.util.Global.SECURITY_CODE_USERDELETE;
import static com.artbrain.util.Global.SECURITY_CODE_USERSTOP;
import static com.artbrain.util.Global.SECURITY_CODE_WRONGPASSWORD;

/**
 * Created by hongyu on 2017/4/7.
 */
@CommonsLog
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        //接受从表单传过来的参数
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.debug("username: " + username + " password: " + password);

        //调用本地重写的加载用户方法（在这个项目里可能是手机号，邮箱）加载用户的验证所需信息。（可以写DTO，包括id，salt，password。在这里为了省事直接加载所有信息）
        MyUserDetails user = (MyUserDetails) myUserDetailsService.loadUserByUsername(username);

        if (user == null) {
            log.debug("未找到用户");
            throw new BadCredentialsException("未找到用户");
        }

        //查看用户是否停用
        if (user.getIsDel() == 1) {
            log.debug("用户已停用code:" + SECURITY_CODE_USERSTOP + ".id:" + user.getId());
            throw new BadCredentialsException("用户已停用code:" + SECURITY_CODE_USERSTOP + ".id:" + user.getId());
        }
        //查看用户是否删除
        if (user.getIsStop() == 1) {
            log.debug("用户已逻辑删除code:" + SECURITY_CODE_USERDELETE + ".id:" + user.getId());
            throw new BadCredentialsException("用户已逻辑删除code:" + SECURITY_CODE_USERDELETE + ".id:" + user.getId());
        }

        //验证密码
        String hashPassword = user.getPassword();
        String salt = user.getSalt();
        if (!CryptoUtils.verify(hashPassword, password, salt)) {
            log.debug("密码不匹配code:" + SECURITY_CODE_WRONGPASSWORD + ".id:" + user.getId());
            throw new BadCredentialsException("密码不匹配code:" + SECURITY_CODE_WRONGPASSWORD + ".id:" + user.getId());
        }

        //查看用户对应权限
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        log.debug("login success, username: " + username + ", authorities: " + authorities.toString());
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
