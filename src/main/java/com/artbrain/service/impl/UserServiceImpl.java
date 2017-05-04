package com.artbrain.service.impl;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.github.pagehelper.PageHelper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        user = userDao.selectUserById(user);
        return user;
    }

    @Override
    public User userFindByEmail(User user) {
        user = userDao.selectUserByEmail(user);
        return user;
    }

    @Override
    public Boolean isDuplicateEmail(User user) {
        user = userDao.selectUserByEmail(user);
        return null != user;
    }

    @Override
    public Boolean isDuplicateWxId(User user) {
        user = userDao.selectUserByWxId(user);
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
        int effect_row = userDao.addUser(user);
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

    @Override
    public List<User> userFindAll(int page, int rows) {
        PageHelper.startPage(page, rows);
        return userDao.selectAllUser();
    }
    @Override
    public List<User> userFindByClazzId(User user){
        return userDao.selectUserByClazzId(user);
    }
}
