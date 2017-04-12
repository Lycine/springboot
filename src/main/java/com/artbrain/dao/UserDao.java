package com.artbrain.dao;


import com.artbrain.entity.User;


/**
 * Created by hongyu on 2017/1/15.
 */
public interface UserDao {

    User selectUserById(User user);

    int updateUserById(User user);

    int addUserLoginFailureCountById(User user);

    int deleteUserById(User user);

    int realDeleteUserById(User user);

    int addUserById(User user);

    //    登录用
    User selectUserByEmailAndPassword(User user);


    User selectUserByPhoneNumberAndPassword(User user);

    //    检查重复用
    User selectUserByEmail(User user);


    User selectUserByPhoneNumber(User user);



}
