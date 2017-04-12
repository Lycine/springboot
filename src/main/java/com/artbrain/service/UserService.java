package com.artbrain.service;

import com.artbrain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by hongyu on 2017/1/15.
 */
public interface UserService {
    Boolean userLogin(User user);

    Boolean userUpdate(User user);

    Boolean userUpdateLoginFailure(User user);

    User userDetailById(User user);

    Boolean userDelete(User user);

    Boolean userRealDelete(User user);

    Boolean isDuplicateEmail(User user);

    Boolean isDuplicatePhoneNumber(User user);


    Boolean userAdd(User user);

    /**
     * MyUserDetailService用到
     * 通过传邮箱或手机号来登录
     * @param user
     * @return
     */
    User loadUserByUsername(User user);

}
