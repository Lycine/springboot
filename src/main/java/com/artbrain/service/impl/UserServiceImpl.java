package com.artbrain.service.impl;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
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
@CommonsLog
@Service("UserService")
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Boolean userUpdateById(User user) {
        int effect_row = userDao.updateUserById(user);
        log.debug("修改行数：" + effect_row);
        return effect_row > 0;
    }

    @Override
    public User userFindById(User user) {
        System.out.println("old: " + user);
        user = userDao.selectUserById(user);
        System.out.println("new: " + user);
        return user;
    }

    @Override
    public Boolean isDuplicateEmail(User user) {
        user = userDao.selectUserByEmail(user);
        return null != user;
    }

    @Override
    public Boolean isDuplicatePhoneNumber(User user) {
        user = userDao.selectUserByPhoneNumber(user);
        return null != user;
    }

    @Override
    public Boolean isDuplicateNickName(User user) {
        user = userDao.selectUserByNickName(user);
        return null != user;
    }

    @Override
    public Boolean userAdd(User user) {
        int effect_row = userDao.addUserById(user);
        return effect_row > 0;
    }


    @Override
    public User loadUserByUsername(User user) {
        User resultuser = userDao.selectUserByEmail(user);
        if (null == resultuser){
            resultuser = userDao.selectUserByPhoneNumber(user);
        }
        return resultuser;
    }
}
