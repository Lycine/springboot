package com.artbrain.dao.mapper;

import com.artbrain.dao.UserDao;
import com.artbrain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hongyu on 2017/1/15.
 */
@Mapper
public interface UserMapper extends UserDao {
    /**
     * 查找用户所有信息
     * 根据用户ID查找该用户所有信息
     *
     * @param user
     * @return
     */
    @Override
    User selectUserById(@Param("user") User user);

    /**
     * 更新用户所有不为空的信息
     * 根据用户ID修改所有信息
     *
     * @param user
     * @return
     */
    @Override
    int updateUserById(@Param("user") User user);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    int addUser(@Param("user") User user);

    /**
     * 查重邮箱
     * 更新邮箱时查看数据库中邮箱是否已用过
     *
     * @param user
     * @return
     */
    @Override
    User selectUserByEmail(@Param("user") User user);

    /**
     * 查重微信号
     * 更新微信号时查看数据库中微信号是否已用过
     *
     * @param user
     * @return
     */
    @Override
    User selectUserByWxId(@Param("user") User user);

    /**
     * 查重手机号
     * 更新手机号时查看数据库中手机号是否已用过
     *
     * @param user
     * @return
     */
    @Override
    User selectUserByPhoneNumber(@Param("user") User user);

    /**
     * 查重昵称
     * 更新昵称时查看数据库中昵称是否已用过
     *
     * @param user
     * @return
     */
    @Override
    User selectUserByNickName(@Param("user") User user);

    /**
     * 查找全部用户
     *
     * @return
     */
    @Override
    List<User> selectAllUser();

    /**
     * 按班级id查找学生
     *
     * @param user
     * @return
     */
    @Override
    List<User> selectUserByClazzId(@Param("user") User user);
}
