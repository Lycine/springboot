package com.artbrain.controller;

import com.artbrain.entity.*;
import com.artbrain.service.BulletinService;
import com.artbrain.service.ClazzService;
import com.artbrain.service.TokenService;
import com.artbrain.service.UserService;
import com.artbrain.util.RandomStringGenerator;
import com.artbrain.util.TimeStampHelper;
import com.artbrain.util.Validator;
import com.artbrain.util.encryptAndDecode.CryptoUtils;
import com.artbrain.util.mail.SendEmail;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.artbrain.util.Global.*;


@CommonsLog
@Controller
public class PassController {
    public int newFailureCount;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private ClazzService clazzService;

    @RequestMapping(value = "/pass")
    public String signIn() {
        return "pass";
    }

//    @RequestMapping(value = REGISTER_PROCESSIN_CONTROLLER, method = RequestMethod.POST)
//    public String register(User user) {
//        try {
//            if (null == user.getEmail() || "".equals(user.getEmail().trim())) {
//                log.debug("邮箱为空");
//                return "redirect:" + REGISTER_PAGE_WRONGFORMAT;
//            }
//            String email = user.getEmail().trim();
//            if (!Validator.isEmail(email)) {
//                log.debug("邮箱格式不正确: " + email);
//                return "redirect:" + REGISTER_PAGE_WRONGFORMAT;
//            }
//            if (userService.isDuplicateEmail(user)) {
//                log.debug("已注册过的邮箱");
//                return "redirect:" + REGISTER_PAGE_DUPLICATEEMAIL;
//            } else {
//                log.debug("新建用户");
//                String salt = CryptoUtils.getSalt();//盐值
//                String password = user.getPassword();//传过来的明文密码
//                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
//                user.setSalt(salt);
//                user.setPassword(hashPassword);
//                user.setNickName(user.getEmail());
//
//                if (userService.userAdd(user)) {
//                    log.debug("新建用户成功");
//                } else {
//                    log.debug("新建用户失败，dao层错误");
//                    return "redirect:" + REGISTER_PAGE_UNKNOWN;
//                }
//                Token token = new Token();
//                token.setToken(RandomStringGenerator.getRandomString(50));
//                Date dt = new Date();
//                long nowSec = dt.getTime() / 1000;
//                Date expireTime = new Date((nowSec + 36000) * 1000);
//                Date addTime = new Date(nowSec * 1000);
//                token.setExpireTime(expireTime);
//                token.setAddTime(addTime);
//                token.setMaster("ACTIVE_ACCOUNT");
//                user = userService.userFindByEmail(user);
//                token.setRemark(String.valueOf(user.getId()));
//                if (tokenService.isDuplicateToken(token)) {
//                    log.debug("重置密码失败，token重复");
//                    return "redirect:" + REGISTER_PAGE_UNKNOWN;
//                } else {
//                    tokenService.tokenAdd(token);
//                    SendEmail sendEmail = new SendEmail();
//                    String link = "http://" + HOSTNAME_AND_PORT + "/pass/signIn/active/" + user.getId() + "_" + token.getToken();
//                    if (sendEmail.sendMailActiveAccount(email, link)) {
//                        log.debug("邮件发送成功");
//                        return "redirect:" + REGISTER_PAGE_SUCCESS;
//                    } else {
//                        log.debug("邮件发送失败");
//                        return "redirect:" + REGISTER_PAGE_SENDFAILURE;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.debug("新建用户失败，未知错误");
//            e.printStackTrace();
//            return "redirect:" + REGISTER_PAGE_UNKNOWN;
//        }
//
//    }

    @RequestMapping(value = SIGNIN_SUCCESS_CONTROLLER)
    public String loginSuccess(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (userService.userUpdateById(user)) {
            log.debug("登录信息保存成功");
        } else {
            log.debug("登录信息保存失败");
        }
        user = userService.userFindById(user);
        session.setAttribute("user", user);
        switch (user.getRole()) {
            case "ROLE_STUDENT":
                Bulletin bulletin = bulletinService.bulletinLastFindByStuId(user);
                if (null != bulletin){
                    model.addAttribute("bulletin", bulletin);
                    log.debug(bulletin.toString());
                } else {
                    log.debug("该用户暂无公告");
                }
                List<String> clazzNames = new ArrayList<>();
                String[] clazzIds = user.getClazz().split(",");
                for (String clazzId : clazzIds){
                    Clazz clazz = new Clazz();
                    clazz.setId(Integer.parseInt(clazzId));
                    clazz = clazzService.clazzFindById(clazz);
                    clazzNames.add(clazz.getName());
                }

                model.addAttribute("userClazzNames",clazzNames);
                log.debug(clazzNames.toString());
                log.debug(user.toString());
                return "/stu/stu_index";
            case "ROLE_TEACHER":
                return "/tea/tea_index";
            case "ROLE_ADMIN":

                return "/admin/admin_index";
            default:
                return "pass?signIn&normalFailure";
        }
    }

    @RequestMapping(value = SIGNIN_FAILURE_CONTROLLER + "{signInFailureCode}/{id}")
    public String loginFailure(@PathVariable int id, @PathVariable int signInFailureCode) {
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
        if (id == 0 || signInFailureCode == SIGNIN_FAILURE_UNKNOWN_CODE) {
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

    /**
     * 点击找回密码（邮箱找回密码发送邮件）
     * 查看是否存在该邮箱
     * 如果存在发送一封带url的邮件（特定controller／token=xxx）
     * 用户点进入url重置密码。返回到登录页面
     *
     * @param user
     * @return
     */
    @RequestMapping(value = FORGET_PASSWORD_SEND_EMAIL_CONTROLLER, method = RequestMethod.POST)
    public String requestForgetPasswordMailLink(User user) {
        try {
            if (null == user.getEmail() || "".equals(user.getEmail().trim())) {
                log.debug("邮箱为空");
                return "redirect:" + FORGET_PASSWORD_PAGE_EMPTYMAIL;
            }
            String email = user.getEmail().trim();
            if (!Validator.isEmail(email)) {
                log.debug("邮箱格式不正确: " + email);
                return "redirect:" + FORGET_PASSWORD_PAGE_WRONGFORMAT;
            }
            if (!userService.isDuplicateEmail(user)) {
                log.debug("未注册过的邮箱(没有用户)");
                return "redirect:" + FORGET_PASSWORD_PAGE_USERNOTFOUND;
            } else {
                //sendemail
                Token token = new Token();
                token.setToken(RandomStringGenerator.getRandomString(50));
                Double timeStamp = TimeStampHelper.getTimeStamp();
                Date dt = new Date();
                long nowSec = dt.getTime() / 1000;
                Date expireTime = new Date((nowSec + 3600) * 1000);
                Date addTime = new Date(nowSec * 1000);
                token.setExpireTime(expireTime);
                token.setAddTime(addTime);
                token.setMaster("FORGET_PASSWORD");
                user = userService.userFindByEmail(user);
                token.setRemark(String.valueOf(user.getId()));
                if (tokenService.isDuplicateToken(token)) {
                    log.debug("重置密码失败，token重复");
                    return "redirect:" + FORGET_PASSWORD_PAGE_ERRORLINK;
                } else {
                    tokenService.tokenAdd(token);
                    SendEmail sendEmail = new SendEmail();
                    String link = "http://" + HOSTNAME_AND_PORT + "/pass?forgetPassword&token=" + token.getToken() + "&uid=" + user.getId();
                    if (sendEmail.sendMailResetPassword(email, link)) {
                        log.debug("邮件发送成功");
                        return "redirect:" + FORGET_PASSWORD_PAGE_SUCCESS;
                    } else {
                        log.debug("邮件发送失败");
                        return "redirect:" + FORGET_PASSWORD_PAGE_SENDFAILURE;
                    }
                }
            }
        } catch (Exception e) {
            log.debug("重置密码失败，未知错误");
            e.printStackTrace();
            return "redirect:" + FORGET_PASSWORD_PAGE_UNKNOWN;
        }
    }

    /**
     * 通过链接找回密码
     * 得到token，密码
     * 重置密码
     * 返回登录
     *
     * @param resetPasswordDto
     * @return
     */
    @RequestMapping(value = FORGET_PASSWORD_RESET_PASSWORD_BY_LINK_CONTROLLER, method = RequestMethod.POST)
    public String resetPasswordByLink(ResetPasswordDto resetPasswordDto, HttpServletRequest request) {
        Token token = new Token();
        User user = new User();
        try {
            if (null == resetPasswordDto.getUid() || "".equals(resetPasswordDto.getUid()) || "0".equals(resetPasswordDto.getUid())) {
                log.debug("uid为空");
                return "redirect:" + FORGET_PASSWORD_PAGE_ERRORLINK + "&token=0&uid=0";
            } else {
                user.setId(Integer.parseInt(resetPasswordDto.getUid()));
            }
            if (null == resetPasswordDto.getToken() || "".equals(resetPasswordDto.getToken()) || "0".equals(resetPasswordDto.getToken())) {
                log.debug("token为空");
                return "redirect:" + FORGET_PASSWORD_PAGE_ERRORLINK + "&token=0&uid=0";
            } else {
                token.setToken(resetPasswordDto.getToken());
            }
            log.debug(resetPasswordDto.toString());
            user = userService.userFindById(user);
            String email = user.getEmail().trim();
            if (!userService.isDuplicateEmail(user)) {
                log.debug("未注册过的邮箱(没有用户)");
                return "redirect:" + FORGET_PASSWORD_PAGE_USERNOTFOUND + "&token=0&uid=0";
            }
            Date dt = new Date();
            long nowSec = dt.getTime();
            token = tokenService.tokenFindByToken(token);
            if (null == token) {
                log.debug("token不存在");
                return "redirect:" + FORGET_PASSWORD_PAGE_ERRORLINK + "&token=0&uid=0";
            }
//            两个long类型比较
            if (nowSec < token.getExpireTime().getTime()) {
                log.debug("token未过期");
                String salt = CryptoUtils.getSalt();//盐值
                String password = user.getPassword();//传过来的明文密码
                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
                user.setSalt(salt);
                user.setPassword(hashPassword);
                if (!userService.userUpdateById(user)) {
                    log.debug("重置密码失败，未知错误。");
                    return "redirect:" + FORGET_PASSWORD_PAGE_UNKNOWN + "&token=0&uid=0";
                } else {
                    log.debug("重置密码成功。");
                    tokenService.tokenExpiredDelete();
                    if (tokenService.tokenDeleteById(token)) {
                        log.debug("已删除次token。tokenId: " + token.getId());
                    }
                    return "redirect:" + FORGET_PASSWORD_PAGE_SUCCESS + "&token=0&uid=0";
                }
            } else {
                log.debug("token已过期");
                return "redirect:" + FORGET_PASSWORD_PAGE_TOKEN_EXPIRE + "&token=0&uid=0";
            }
        } catch (Exception e) {
            log.debug("重置密码失败，未知错误");
            e.printStackTrace();
            return "redirect:" + FORGET_PASSWORD_PAGE_UNKNOWN + "&token=0&uid=0";
        }
    }

    @RequestMapping(value = ACTIVE_ACCOUNT_PROCESSIN_CONTROLLER + "/{uid}_{tokenString}", method = RequestMethod.GET)
    public String register(@PathVariable("uid") String uid, @PathVariable("tokenString") String tokenString) {
        try {
            if (null == uid || "".equals(uid)) {
                log.debug("uid为空");
                return "redirect:" + SIGNIN_PAGE_ACTIVE_ERRORLINK;
            }
            if (null == tokenString || "".equals(tokenString)) {
                log.debug("token为空");
                return "redirect:" + SIGNIN_PAGE_ACTIVE_ERRORLINK;
            } else {
                Date dt = new Date();
                long nowSec = dt.getTime();
                Token token = new Token();
                token.setToken(tokenString);
                token = tokenService.tokenFindByToken(token);
                if (null == token) {
                    log.debug("token不存在");
                    return "redirect:" + SIGNIN_PAGE_ACTIVE_ERRORLINK;
                }
//            两个long类型比较
                if (nowSec < token.getExpireTime().getTime()) {
                    log.debug("token未过期");
                    User user = new User();
                    user.setId(Integer.parseInt(uid));
                    user.setIsStop(0);
                    if (!userService.userUpdateById(user)) {
                        log.debug("激活失败，未知错误。");
                        return "redirect:" + SIGNIN_PAGE_ACTIVE_UNKNOWN;
                    } else {
                        log.debug("重置密码成功。");
                        tokenService.tokenExpiredDelete();
                        if (tokenService.tokenDeleteById(token)) {
                            log.debug("已删除次token。tokenId: " + token.getId());
                        }
                        return "redirect:" + SIGNIN_PAGE_ACTIVE_SUCCESS;
                    }
                } else {
                    log.debug("token已过期");
                    return "redirect:" + SIGNIN_PAGE_ACTIVE_ERRORLINK;
                }
            }
        } catch (Exception e) {
            log.debug("新建用户失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKNOWN;
        }

    }

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
        user = userService.userFindById(user);
        int alreadyFailureCount = user.getLoginFailureCount();
        newFailureCount = alreadyFailureCount + 1;
        if (newFailureCount > SIGNIN_FAILURE_THRESHOLD) {
            log.debug("登录失败次数大于阈值: " + newFailureCount);
            user.setIsStop(1);
            userService.userUpdateById(user);
            return true;
        }
        log.debug("登录失败加一后: " + newFailureCount);
        user.setLoginFailureCount(newFailureCount);
        userService.userUpdateById(user);
        return false;
    }

}
