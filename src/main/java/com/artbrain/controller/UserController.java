package com.artbrain.controller;

import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.artbrain.util.CryptoUtils;
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

    @RequestMapping(value = "/student/update", method = RequestMethod.POST)
    public String studentUpdate(User user, Model model, HttpSession session, HttpServletRequest request) {
        Date date = new Date();
        user.setUpdateTime(date);
        String salt = CryptoUtils.getSalt();//盐值
        String password = user.getPassword();//传过来的明文密码
        String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
        user.setSalt(salt);
        user.setPassword(hashPassword);
        log.info(user.toString());
        if (null == user.getEmail() && "".equals(user.getEmail())) {
            model.addAttribute("msg", "更新失败，邮箱为空。");
            log.debug("更新失败，邮箱为空。");
            return "redirect:/home?failure";
        } else if (null == user.getPhoneNumber() && "".equals(user.getPhoneNumber())) {
            model.addAttribute("msg", "更新失败，手机号为空。");
            log.debug("更新失败，手机号为空。");
            return "redirect:/home?failure";
        } else if (!Validator.isEmail(user.getEmail())){
            model.addAttribute("msg", "更新失败，邮箱格式不正确。");
            log.debug("更新失败，邮箱格式不正确。");
            return "redirect:/home?failure";
        } else if (!Validator.isMobile(user.getPhoneNumber())){
            model.addAttribute("msg", "更新失败，手机号格式不正确。");
            log.debug("更新失败，手机号格式不正确。");
            return "redirect:/home?failure";
        } else if (userService.isDuplicatePhoneNumber(user)) {
            model.addAttribute("msg", "更新失败，手机号已存在。");
            log.debug("更新失败，手机号已存在。");
            return "redirect:/home?failure";
        } else if (userService.isDuplicateEmail(user)) {
            model.addAttribute("msg", "更新失败，邮箱已存在。");
            log.debug("更新失败，邮箱已存在。");
            return "redirect:/home?failure";
        } else if (!userService.userUpdateById(user)) {
            model.addAttribute("msg", "更新失败，未知错误。");
            log.debug("更新失败，未知错误。");
            return "redirect:/home?failure";
        } else {
            model.addAttribute("msg", "更新成功。");
            model.addAttribute("success");
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新成功。");
//            return "redirect:/home?success";
            return "home";
        }
    }
}
