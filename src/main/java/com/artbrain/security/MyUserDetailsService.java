package com.artbrain.security;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import com.artbrain.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by hongyu on 2017/4/7.
 */
@Service("MyUserDetailsImpl")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        System.out.println("--MyUserDetailsService");
        System.out.println("loginName: "+ loginName);
        User user = new User();
        try {
//            user = userServiceImpl.findByName(userName);
            user.setLoginName(loginName);
            user = userDao.selectUserByloginName(user);
            if (null == user){
                System.out.println("not use loginname");
                user = new User();
                user.setEmail(loginName);
                user = userDao.selectUserByEmail(user);
            } else if (null == user) {
                System.out.println("not use email");
                user = new User();
                user.setPhoneNumber(loginName);
                user = userDao.selectUserByPhoneNumber(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("user select fail");
            throw new UsernameNotFoundException("user select fail");
        }
        if(user == null || user.getId() == 0){
            System.out.println("no user found");
            throw new UsernameNotFoundException("no user found");
        } else {
            return new MyUserDetails(user);
        }
    }
}
