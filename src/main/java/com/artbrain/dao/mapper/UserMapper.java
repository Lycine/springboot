package com.artbrain.dao.mapper;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@Mapper
public interface UserMapper extends UserDao {

    @Override
    User selectUserById(@Param("user") User user);

    @Override
    int updateUserById(@Param("user") User user);

    @Override
    int addUserLoginFailureCountById(@Param("user") User user);

    @Override
    int deleteUserById(@Param("user") User user);

    @Override
    int realDeleteUserById(@Param("user") User user);

    @Override
    int addUserById(@Param("user") User user);

    //    登录用
    @Override
    User selectUserByEmailAndPassword(@Param("user") User user);

    @Override
    User selectUserByloginNameAndPassword(@Param("user") User user);

    @Override
    User selectUserByPhoneNumberAndPassword(@Param("user") User user);

    //    检查重复用
    @Override
    User selectUserByEmail(@Param("user") User user);

    @Override
    User selectUserByloginName(@Param("user") User user);

    @Override
    User selectUserByPhoneNumber(@Param("user") User user);
}
