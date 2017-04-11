package com.artbrain.controller;

import com.artbrain.entity.User;
import com.artbrain.service.UserService;
import com.artbrain.util.GetRealIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home")
    public String home(User newUser, Model model, HttpSession session,HttpServletRequest request) {

        newUser = (User) session.getAttribute("user");
        model.addAttribute("user",newUser);
        return "home";
    }
}
