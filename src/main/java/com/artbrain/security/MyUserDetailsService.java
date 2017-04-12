package com.artbrain.security;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.artbrain.service.impl.UserServiceImpl;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by hongyu on 2017/4/7.
 */
@CommonsLog
@Service("MyUserDetailsImpl")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    /**
     * 重写的加载用户方法，可以通过手机号，邮箱
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("MyAuthPro传过来的username: " + username);
        User user = new User();
        user.setPhoneNumber(username);
        user.setEmail(username);
        user = userService.loadUserByUsername(user);
        if (null == user) {
            log.debug("未找到用户: " + username);
            throw new UsernameNotFoundException("未找到用户");
        }
        return new MyUserDetails(user);
    }
}
