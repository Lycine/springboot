package com.artbrain.security;

import com.artbrain.util.Global;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            if (null != loginFailureId && !"".equals(loginFailureId) && Integer.parseInt(loginFailureId) == 0) {
                httpServletResponse.sendRedirect(SIGNIN_FAILURE_USERNOTFOUND_CONTROLLER + loginFailureId);
                return;
            }
            //密码不匹配
            if (security_code == Global.SIGNIN_FAILURE_WRONGPASSWORD_CODE) {
                httpServletResponse.sendRedirect(SIGNIN_FAILURE_WRONGPASSWORD_CONTROLLER + loginFailureId);
                return;
            }
            //已停用
            if (security_code == Global.SIGNIN_FAILURE_USERSTOP_CODE) {//
                httpServletResponse.sendRedirect(SIGNIN_FAILURE_USERSTOP_CONTROLLER + loginFailureId);
                return;
            }
            //已逻辑删除
            if (security_code == Global.SIGNIN_FAILURE_USERDELETE_CODE) {
                httpServletResponse.sendRedirect(SIGNIN_FAILURE_USERDELETE_CONTROLLER + loginFailureId);
                return;
            }
            if (security_code == Global.SIGNIN_FAILURE_USERNOTFOUND_CODE){
                httpServletResponse.sendRedirect(SIGNIN_FAILURE_USERNOTFOUND_CONTROLLER + 0);
                return;
            }
            //未知错误
            log.debug("未知错误authE: " + authE.getMessage());
            httpServletResponse.sendRedirect(SIGNIN_FAILURE_UNKWON_CONTROLLER);


        } catch (Exception e) {
            //未知错误
            log.debug("未知错误authE: " + authE.getMessage());
            log.debug("未知错误e: " + e.getMessage());
            httpServletResponse.sendRedirect(SIGNIN_FAILURE_UNKWON_CONTROLLER);
        }
    }
}
