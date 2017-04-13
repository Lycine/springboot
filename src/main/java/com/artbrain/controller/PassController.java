package com.artbrain.controller;

import com.artbrain.entity.User;

import com.artbrain.service.UserService;
import com.artbrain.util.CryptoUtils;
import com.artbrain.util.Validator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.artbrain.util.Global.*;


@CommonsLog
@Controller
public class PassController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pass")
    public String signIn(User newUser, Model model) {
        return "pass";
    }

    @RequestMapping(value = REGISTER_PROCESSIN_CONTROLLER, method = RequestMethod.POST)
    public String register(User newUser, Model model) {
        try {
            if (null == newUser.getEmail() || "".equals(newUser.getEmail().trim())){
                log.debug("邮箱为空");
                return "redirect:" + REGISTER_PAGE_WRONGFORMAT;
            }
            String email = newUser.getEmail().trim();
            if (!Validator.isEmail(email)) {
                log.debug("邮箱格式不正确: " + email);
                return "redirect:" + REGISTER_PAGE_WRONGFORMAT;
            }
            if (userService.isDuplicateEmail(newUser)) {
                log.debug("已注册过的邮箱");
                return "redirect:" + REGISTER_PAGE_DUPLICATEEMAIL;
            } else {
                log.debug("新建用户");
                String salt = CryptoUtils.getSalt();//盐值
                String password = newUser.getPassword();//传过来的明文密码
                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
                newUser.setSalt(salt);
                newUser.setPassword(hashPassword);
                newUser.setNickName(newUser.getEmail());
                if (userService.userAdd(newUser)) {
                    log.debug("新建用户成功");
                } else {
                    log.debug("新建用户失败，dao层错误");
                }
                return "redirect:" + REGISTER_PAGE_SUCCESS;
            }
        } catch (Exception e) {
            log.debug("新建用户失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKWON;
        }

    }

    @RequestMapping(value = SIGNIN_SUCCESS_CONTROLLER)
    public String loginSuccess(User user, RedirectAttributes attr, HttpSession session, HttpServletRequest request) {
        user = (User) session.getAttribute("user");
        if (userService.userUpdate(user)) {
            log.debug("登录信息保存成功");
        } else {
            log.debug("登录信息保存失败");
        }
        user = userService.userDetailById(user);
        session.setAttribute("user", user);
        return "redirect:/home";
    }

    @RequestMapping(value = SIGNIN_FAILURE_CONTROLLER + "{signInFailureCode}/{id}")
    public String loginFailure(@PathVariable int id, @PathVariable int signInFailureCode, RedirectAttributes attr, HttpSession session, HttpServletRequest request) {
        User user = new User();
        //停用
        if (signInFailureCode == SIGNIN_FAILURE_USERSTOP_CODE) {
            return "redirect:" + SIGNIN_PAGE_STOP;
        }

        //已逻辑删除
        if (signInFailureCode == SIGNIN_FAILURE_USERDELETE_CODE) {
            return "redirect:" + SIGNIN_PAGE_DELETE;
        }

        //未找到用户
        if (signInFailureCode == SIGNIN_FAILURE_USERNOTFOUND_CODE) {
            return "redirect:" + SIGNIN_PAGE_USERNOTFOUND;//显示成密码错误
        }

        //密码错误
        if (signInFailureCode == SIGNIN_FAILURE_WRONGPASSWORD_CODE) {
            if (signInFailureIsStop(user, id)) {
                return "redirect:" + SIGNIN_PAGE_STOP;
            } else {
                return "redirect:" + SIGNIN_PAGE_NORMALFAILURE + newFailureCount;
            }
        }

        //未找到用户，或未知错误
        if (id == 0 || signInFailureCode == SIGNIN_FAILURE_UNKWON_CODE) {
            return "redirect:" + SIGNIN_PAGE_ABNORMALFAILURE;
        }

        //未知错误
        return "redirect:" + SIGNIN_PAGE_ABNORMALFAILURE;
    }

    @RequestMapping(value = LOGOUT_PROCESSIN_CONTROLLER, method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:" + LOGOUT_SUCCESS_URL;
    }

    public int newFailureCount;

    /**
     * 登录失败所做的处理
     * 查看是否达到阈值，做出是否停用的判断。
     * 如果达到阈值，更新用户信息为已停用，返回true。之后跳转到已停用html模版
     * 如果没有达到阈值，登录错误次数+1，更新用户信息登录失败次数，返回false。之后根据错误类型跳转到不同html模版
     *
     * @param user
     * @param id
     * @return
     */
    public boolean signInFailureIsStop(User user, int id) {
        user.setId(id);
        user = userService.userDetailById(user);
        int alreadyFailureCount = user.getLoginFailureCount();
        newFailureCount = alreadyFailureCount + 1;
        if (newFailureCount > SIGNIN_FAILURE_THRESHOLD) {
            log.debug("登录失败次数大于阈值: " + newFailureCount);
            user.setIsStop(1);
            userService.userUpdate(user);
            return true;
        }
        log.debug("登录失败加一后: " + newFailureCount);
        user.setLoginFailureCount(newFailureCount);
        userService.userUpdateLoginFailure(user);
        user.setLoginFailureCount(newFailureCount);
        return false;
    }
}
