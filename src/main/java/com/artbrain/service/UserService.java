package com.artbrain.service;

import com.artbrain.entity.User;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
public interface UserService {
    /**
     * 更新用户所有不为空的信息
     * 根据用户ID修改所有信息
     *
     * @param user
     * @return
     */
    Boolean userUpdateById(User user);

    /**
     * 查找用户所有信息
     * 根据用户ID查找该用户所有信息
     *
     * @param user
     * @return
     */
    User userFindById(User user);

    /**
     * 查找用户所有信息
     * 根据用户email查找该用户所有信息
     *
     * @param user
     * @return
     */
     User userFindByEmail(User user);
    /**
     * 查重邮箱
     * 更新邮箱时查看数据库中邮箱是否已用过
     *
     * @param user
     * @return
     */
    Boolean isDuplicateEmail(User user);

    /**
     * 查重手机号
     * 更新手机号时查看数据库中手机号是否已用过
     *
     * @param user
     * @return
     */
    Boolean isDuplicatePhoneNumber(User user);

    /**
     * 查重昵称
     * 更新昵称时查看数据库中昵称是否已用过
     *
     * @param user
     * @return
     */
    Boolean isDuplicateNickName(User user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    Boolean userAdd(User user);

    /**
     * MyUserDetailService用到
     * 通过传邮箱或手机号来登录
     *
     * @param user
     * @return
     */
    User loadUserByUsername(User user);

    /**
     * 查找所有用户
     *
     * @param page
     * @param rows
     * @return
     */
    List<User> userFindAll(int page, int rows);
}
