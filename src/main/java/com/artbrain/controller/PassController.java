package com.artbrain.controller;

import com.artbrain.entity.User;

import com.artbrain.service.UserService;
import com.artbrain.util.CryptoUtils;
import com.artbrain.util.GetRealIp;
import com.artbrain.util.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Date;

@Controller
@RequestMapping(value = "/pass")
public class PassController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String signIn(User newUser, Model model) {
        return "pass";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(User newUser, Model model) {
        System.out.println("newuser: " + newUser.toString());

        try {
            if (null != newUser.getEmail() && !"".equals(newUser.getEmail()) && userService.isDuplicateEmail(newUser)) {
                System.out.println("This email is already registered");
                return "redirect:" + "/pass?register&registerFailed=1";
            } else {
                System.out.println("addUser");
                CryptoUtils cryptoUtils = new CryptoUtils();
                String salt = CryptoUtils.getSalt();
                String password = newUser.getPassword();
                String hashPassword = CryptoUtils.getHash(password, salt);    //加密的密码
                System.out.println("hashPassword:" + hashPassword);
                System.out.println("salt:" + salt);        //盐值
                System.out.println("password:" + password);
                newUser.setSalt(salt);
                newUser.setPassword(hashPassword);
                newUser.setNickName(newUser.getEmail());
                if (userService.userAdd(newUser)) {
                    System.out.println("add success");
                } else {
                    System.out.println("add failure");
                }
                return "redirect:" + "/pass?register&success";
            }
        } catch (Exception e) {
            System.out.println("register failed: server error");
            e.printStackTrace();
            return "redirect:" + "/pass?register&registerFailed=0";
        }

    }

    @RequestMapping(value = "/loginSuccess")
    public String loginSuccess(User newUser, RedirectAttributes attr, HttpSession session, HttpServletRequest request) {

        newUser = (User) session.getAttribute("user");

        String ip = GetRealIp.getIpAddr(request);
        Date date = new Date();
        newUser.setLastTime(date);
        newUser.setLoginIp(ip);
        System.out.println("savelogininfo: " + newUser.toString());
        userService.userUpdate(newUser);

        System.out.println("newUser: " + newUser.toString());
        User detailUser = userService.userDetailById(newUser);
        System.out.println("detailUser: " + detailUser.toString());
        session.setAttribute("user", detailUser);
        return "redirect:/home";
    }

    @RequestMapping(value = "/loginFailure/{id}")
    public String loginFailure(@PathVariable int id, RedirectAttributes attr, HttpSession session, HttpServletRequest request) {
        if (id == 0 ){
            return "redirect:/pass?signIn&signInFailed";
        }
        System.out.println("loginFailureConId: " + id);
        User user = new User();
        user.setId(id);
        user = userService.userDetailById(user);
        if (user.getIsDel() == 1){
            return "redirect:/pass?signIn&signInDelete";
        }
        if (user.getIsStop() == 1){
            return "redirect:/pass?signIn&signInLock";
        }
        int alreadyFailureCount = user.getLoginFailureCount();
        int newFailureCount = alreadyFailureCount + 1;
        if (newFailureCount > Global.getSignInFailureThreshold()) {
            user.setIsStop(1);
            userService.userUpdate(user);
            return "redirect:/pass?signIn&signInLock";
        }
        System.out.println(alreadyFailureCount + "," + newFailureCount);
        user.setLoginFailureCount(newFailureCount);
        System.out.println("loginFailureUser: " + user.toString());
        userService.userUpdateLoginFailure(user);
        return "redirect:/pass?signIn&signInFailed&count="+newFailureCount;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/pass?signIn&logoutSuccess";
    }
}
