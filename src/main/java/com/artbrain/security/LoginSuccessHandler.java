package com.artbrain.security;

import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.artbrain.util.GetRealIp;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.artbrain.util.Global.SIGNIN_SUCCESS_CONTROLLER;

/**
 * Created by hongyu on 2017/4/10.
 */
@CommonsLog
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String ip = GetRealIp.getIpAddr(httpServletRequest);
        Date date = new Date();
        user.setLastTime(date);
        user.setLoginIp(ip);
        user.setLoginFailureCount(0);
        log.debug(user.toString());
        httpServletRequest.getSession().setAttribute("user", user);
        httpServletResponse.sendRedirect(SIGNIN_SUCCESS_CONTROLLER);
    }
}
