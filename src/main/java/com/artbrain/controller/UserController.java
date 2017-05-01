package com.artbrain.controller;

import com.artbrain.entity.Msg;
import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.artbrain.util.encryptAndDecode.CryptoUtils;
import com.artbrain.util.Validator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@CommonsLog
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home")
    public String home(User user, Model model, HttpSession session, HttpServletRequest request) {
        user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "home";
    }

    @RequestMapping(value = "/student/updateBasic", method = RequestMethod.POST)
    public String studentUpdateBasic(User user, Model model, HttpSession session, HttpServletRequest request) {
        Date date = new Date();
        user.setUpdateTime(date);
        User oldUser = userService.userFindById(user);
        log.info(user.toString());
        if (null == user.getEmail() && "".equals(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱为空。");
            return "home";
        } else if (null == user.getPhoneNumber() && "".equals(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号为空。");
            return "home";
        } else if (null == user.getNickName() && "".equals(user.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，昵称为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，昵称为空。");
            return "home";
        } else if (!Validator.isEmail(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱格式不正确。");
            return "home";
        } else if (!Validator.isMobile(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号格式不正确。");
            return "home";
        } else if (userService.isDuplicatePhoneNumber(user) && !user.getPhoneNumber().equals(oldUser.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号已存在。");
            return "home";
        } else if (userService.isDuplicateEmail(user) && !user.getEmail().equals(oldUser.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱已存在。");
            return "home";
        } else if (userService.isDuplicateNickName(user) && !user.getNickName().equals(oldUser.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，昵称已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，昵称已存在。");
            return "home";
        } else if (!userService.userUpdateById(user)) {
            Msg msg = new Msg("failure", "更新基本信息失败，未知错误。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，未知错误。");
            return "home";
        } else {
            Msg msg = new Msg("success", "更新基本信息成功。");
            model.addAttribute("msg", msg);
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新基本信息成功。");
            return "home";
        }
    }

    @RequestMapping(value = "/student/updatePassword", method = RequestMethod.POST)
    public String studentUpdatePassword(User user, Model model, HttpSession session, HttpServletRequest request) {
        Date date = new Date();
        user.setUpdateTime(date);
        User oldUser = userService.userFindById(user);
        String oldPassword = request.getParameter("oldPassword");


        log.info(user.toString());
        if (null == user.getPassword() && "".equals(user.getPassword())) {
            Msg msg = new Msg("failure", "更新密码失败，密码为空。");
            model.addAttribute("msg", msg);
            log.debug("更新密码失败，密码为空。");
            return "home";
        } else if (null == oldPassword && "".equals(oldPassword)) {
            Msg msg = new Msg("failure", "更新密码失败，旧密码为空。");
            model.addAttribute("msg", msg);
            log.debug("更新密码失败，旧密码为空。");
            return "home";
        }
        //验证密码
        String hashPassword = oldUser.getPassword();
        String salt = oldUser.getSalt();
        log.debug("hashPassword: " + hashPassword);
        log.debug("password: " + oldPassword);
        log.debug("salt: " + salt);
        if (!CryptoUtils.verify(hashPassword, oldPassword, salt)) {
            Msg msg = new Msg("failure", "更新密码失败，旧密码验证未通过。");
            model.addAttribute("msg", msg);
            log.debug("更新密码失败，旧密码验证未通过。");
            return "home";
        }
        //修改密码
        salt = CryptoUtils.getSalt();//盐值
        String password = user.getPassword();//传过来的明文密码
        hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
        user.setSalt(salt);
        user.setPassword(hashPassword);
        if (!userService.userUpdateById(user)) {
            Msg msg = new Msg("failure", "更新密码失败，未知错误。");
            model.addAttribute("msg", msg);
            log.debug("更新密码失败，未知错误。");
            return "home";
        } else {
            Msg msg = new Msg("success", "更新密码成功。");
            model.addAttribute("msg", msg);
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新密码成功。");
            return "home";
        }
    }
}
