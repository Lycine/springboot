package com.artbrain.service.impl;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Boolean userLogin(User user) {
        return null;
    }

    @Override
    public Boolean userUpdate(User user) {
        int effect_row = userDao.updateUserById(user);
        System.out.println("effect_row: " + effect_row);
        if (effect_row > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean userUpdateLoginFailure(User user) {
        int effect_row = userDao.addUserLoginFailureCountById(user);
        System.out.println("effect_row: " + effect_row);
        if (effect_row > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User userDetailById(User user) {
        System.out.println("old: " + user);
        user = userDao.selectUserById(user);
        System.out.println("new: " + user);
        return user;
    }

    @Override
    public Boolean userDelete(User user) {
        return null;
    }

    @Override
    public Boolean userRealDelete(User user) {
        return null;
    }

    @Override
    public Boolean isDuplicateEmail(User user) {
        System.out.println("old: " + user);
        user = userDao.selectUserByEmail(user);
        System.out.println("new: " + user);
        if (null == user) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isDuplicatePhoneNumber(User user) {
        return null;
    }

    @Override
    public Boolean isDuplicateLoginName(User user) {
        user = userDao.selectUserByloginName(user);
        if (null == user) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean userAdd(User user) {
        int effect_row = userDao.addUserById(user);
        if (effect_row > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = new User();
        user.setLoginName(s);
        user = userDao.selectUserById(user);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。

        return new org.springframework.security.core.userdetails.User(user.getLoginName(),
                user.getPassword(), authorities);
    }
}
