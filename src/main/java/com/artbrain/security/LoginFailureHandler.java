package com.artbrain.security;

import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
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

import static com.artbrain.util.Global.*;

/**
 * Created by hongyu on 2017/4/10.
 */
@CommonsLog
public class LoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authE) throws IOException, ServletException {
        try {
            int security_code = Integer.parseInt(authE.getMessage().split("code:")[1].split("\\.id:")[0]);
            String loginFailureId = authE.getMessage().split("\\.id:")[1];
            log.debug("security_code: " + security_code + ", loginFailureId: " + loginFailureId);
            if (security_code != 0 && security_code == SECURITY_CODE_WRONGPASSWORD){
                httpServletResponse.sendRedirect(SIGNUP_FAILURE_WRONGPASSWORD_URL+ loginFailureId);
            } else if (security_code == SECURITY_CODE_USERSTOP){
                httpServletResponse.sendRedirect(SIGNUP_FAILURE_USERSTOP_URL+ loginFailureId);
            } else if (security_code == SECURITY_CODE_USERDELETE){
                httpServletResponse.sendRedirect(SIGNUP_FAILURE_USERDELETE_URL+ loginFailureId);
            } else {
                //未知错误
                log.debug("未知错误authE: " + authE.getMessage());
                httpServletResponse.sendRedirect(SIGNUP_FAILURE_UNKWON_URL);
            }

        } catch (Exception e) {
            //未知错误
            log.debug("未知错误authE: " + authE.getMessage());
            log.debug("未知错误e: " + e.getMessage());
            httpServletResponse.sendRedirect(SIGNUP_FAILURE_UNKWON_URL);
        }
    }
}
