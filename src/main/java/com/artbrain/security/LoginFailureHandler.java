package com.artbrain.security;

import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by hongyu on 2017/4/10.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        try {
            String loginFailureId = e.getMessage().split("id:")[1];
            httpServletResponse.sendRedirect("/pass/loginFailure/" + loginFailureId);
        } catch (Exception ee) {
            System.out.println(e.getMessage());
            System.out.println(ee);
            httpServletResponse.sendRedirect("/pass/loginFailure/0");
        }


    }
}
