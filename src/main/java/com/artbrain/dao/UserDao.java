package com.artbrain.dao;


import com.artbrain.entity.User;

import java.util.List;


/**
 * Created by hongyu on 2017/1/15.
 */
public interface UserDao {
    /**
     * 查找用户所有信息
     * 根据用户ID查找该用户所有信息
     *
     * @param user
     * @return
     */
    User selectUserById(User user);

    /**
     * 更新用户所有不为空的信息
     * 根据用户ID修改所有信息
     *
     * @param user
     * @return
     */
    int updateUserById(User user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 查重邮箱
     * 更新邮箱时查看数据库中邮箱是否已用过
     *
     * @param user
     * @return
     */
    User selectUserByEmail(User user);

    /**
     * 查重手机号
     * 更新手机号时查看数据库中手机号是否已用过
     *
     * @param user
     * @return
     */
    User selectUserByPhoneNumber(User user);

    /**
     * 查重昵称
     * 更新昵称时查看数据库中昵称是否已用过
     *
     * @param user
     * @return
     */
    User selectUserByNickName(User user);

    /**
     * 查找全部用户
     *
     * @return
     */
    List<User> selectAllUser();
}
