package com.artbrain.controller;

import com.artbrain.entity.Msg;
import com.artbrain.entity.Token;
import com.artbrain.entity.User;
import com.artbrain.service.ClazzService;
import com.artbrain.service.TokenService;
import com.artbrain.service.UserService;
import com.artbrain.util.RandomStringGenerator;
import com.artbrain.util.Validator;
import com.artbrain.util.encryptAndDecode.CryptoUtils;
import com.artbrain.util.mail.SendEmail;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.artbrain.util.Global.HOSTNAME_AND_PORT;
import static com.artbrain.util.Global.REGISTER_PAGE_UNKNOWN;

@CommonsLog
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/home")
    public String home(User user, Model model, HttpSession session, HttpServletRequest request) {
        user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "stu/stu_index";
    }


    @RequestMapping(value = "/admin/registerNewStudent", method = RequestMethod.POST)
    public String registerNewStudent(User user, Model model) {
        try {
            if (null == user.getEmail() || "".equals(user.getEmail().trim())) {
                log.debug("邮箱为空");
                Msg msg = new Msg("failure", "新建学生失败，邮箱为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            String email = user.getEmail().trim();
            if (!Validator.isEmail(email)) {
                log.debug("邮箱格式不正确: " + email);
                Msg msg = new Msg("failure", "新建学生失败，邮箱格式不正确。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (userService.isDuplicateEmail(user)) {
                log.debug("已注册过的邮箱");
                Msg msg = new Msg("failure", "新建学生失败，已注册过的邮箱。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }

            if (null == user.getNickName() || "".equals(user.getNickName().trim())) {
                log.debug("姓名为空");
                Msg msg = new Msg("failure", "新建学生失败，姓名为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (userService.isDuplicateNickName(user)) {
                log.debug("已存在的姓名");
                Msg msg = new Msg("failure", "新建学生失败，已存在的姓名。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (null == user.getClazz() || "".equals(user.getClazz().trim())) {
                log.debug("班级为空");
                Msg msg = new Msg("failure", "新建学生失败，班级为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            } else {
                log.debug("新建学生");
                user.setNickName(user.getNickName());
                user.setEmail(user.getEmail());
                String salt = CryptoUtils.getSalt();//盐值
//              随机密码
                String randomString = RandomStringGenerator.getRandomString(20);
                String password = randomString.toUpperCase();
                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
                user.setSalt(salt);
                user.setPassword(hashPassword);
                if (userService.userAdd(user)) {
                    log.debug("新建学生成功");
                } else {
                    log.debug("新建学生失败，dao层错误");
                    Msg msg = new Msg("failure", "新建学生失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                }
                Token token = new Token();
                token.setToken(RandomStringGenerator.getRandomString(50));
                Date dt = new Date();
                long nowSec = dt.getTime() / 1000;
                Date expireTime = new Date((nowSec + 36000) * 1000);
                Date addTime = new Date(nowSec * 1000);
                token.setExpireTime(expireTime);
                token.setAddTime(addTime);
                token.setMaster("ACTIVE_ACCOUNT");
                user = userService.userFindByEmail(user);
                token.setRemark(String.valueOf(user.getId()));
                if (tokenService.isDuplicateToken(token)) {
                    log.debug("新建学生失败，token重复");
                    Msg msg = new Msg("failure", "新建学生失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                } else {
                    tokenService.tokenAdd(token);
                    SendEmail sendEmail = new SendEmail();
                    String link = "http://" + HOSTNAME_AND_PORT + "/pass/signIn/active/" + user.getId() + "_" + token.getToken();
                    if (sendEmail.sendMailActiveAccount(email, link, password)) {
                        log.debug("邮件发送成功");
                        Msg msg = new Msg("success", "新建学生成功，请让学生按照邮件激活账户。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    } else {
                        log.debug("邮件发送失败");
                        Msg msg = new Msg("failure", "新建学生失败，未知错误。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    }
                }
            }
        } catch (Exception e) {
            log.debug("新建学生失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKNOWN;
        }

    }

    @RequestMapping(value = "/admin/registerNewTeacher", method = RequestMethod.POST)
    public String registerNewTeacher(User user, Model model) {
        try {
            if (null == user.getEmail() || "".equals(user.getEmail().trim())) {
                log.debug("邮箱为空");
                Msg msg = new Msg("failure", "新建助教失败，邮箱为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            String email = user.getEmail().trim();
            if (!Validator.isEmail(email)) {
                log.debug("邮箱格式不正确: " + email);
                Msg msg = new Msg("failure", "新建助教失败，邮箱格式不正确。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (userService.isDuplicateEmail(user)) {
                log.debug("已注册过的邮箱");
                Msg msg = new Msg("failure", "新建助教失败，已注册过的邮箱。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (null == user.getClazz() || "".equals(user.getClazz().trim())) {
                log.debug("班级为空");
                Msg msg = new Msg("failure", "新建助教失败，班级为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            } else {
                log.debug("新建助教");
                user.setNickName(user.getEmail());
                user.setEmail(user.getEmail());
                String salt = CryptoUtils.getSalt();//盐值
//              随机密码
                String randomString = RandomStringGenerator.getRandomString(20);
                String password = randomString.toUpperCase();
                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
                user.setSalt(salt);
                user.setPassword(hashPassword);
                user.setRole("ROLE_TEACHER");
                if (userService.userAdd(user)) {
                    log.debug("新建助教成功");
                } else {
                    log.debug("新建助教失败，dao层错误");
                    Msg msg = new Msg("failure", "新建助教失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                }
                Token token = new Token();
                token.setToken(RandomStringGenerator.getRandomString(50));
                Date dt = new Date();
                long nowSec = dt.getTime() / 1000;
                Date expireTime = new Date((nowSec + 36000) * 1000);
                Date addTime = new Date(nowSec * 1000);
                token.setExpireTime(expireTime);
                token.setAddTime(addTime);
                token.setMaster("ACTIVE_ACCOUNT");
                user = userService.userFindByEmail(user);
                token.setRemark(String.valueOf(user.getId()));
                if (tokenService.isDuplicateToken(token)) {
                    log.debug("新建助教失败，token重复");
                    Msg msg = new Msg("failure", "新建助教失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                } else {
                    tokenService.tokenAdd(token);
                    SendEmail sendEmail = new SendEmail();
                    String link = "http://" + HOSTNAME_AND_PORT + "/pass/signIn/active/" + user.getId() + "_" + token.getToken();
                    if (sendEmail.sendMailActiveAccount(email, link, password)) {
                        log.debug("邮件发送成功");
                        Msg msg = new Msg("success", "新建助教成功，请让助教按照邮件激活账户。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    } else {
                        log.debug("邮件发送失败");
                        Msg msg = new Msg("failure", "新建助教失败，未知错误。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    }
                }
            }
        } catch (Exception e) {
            log.debug("新建助教失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKNOWN;
        }

    }

    @RequestMapping(value = "/admin/registerNewAdmin", method = RequestMethod.POST)
    public String registerNewAdmin(User user, Model model) {
        try {
            if (null == user.getEmail() || "".equals(user.getEmail().trim())) {
                log.debug("邮箱为空");
                Msg msg = new Msg("failure", "新建管理员失败，邮箱为空。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            String email = user.getEmail().trim();
            if (!Validator.isEmail(email)) {
                log.debug("邮箱格式不正确: " + email);
                Msg msg = new Msg("failure", "新建管理员失败，邮箱格式不正确。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            }
            if (userService.isDuplicateEmail(user)) {
                log.debug("已注册过的邮箱");
                Msg msg = new Msg("failure", "新建管理员失败，已注册过的邮箱。");
                model.addAttribute("msg", msg);
                return "admin/admin_index";
            } else {
                log.debug("新建管理员");
                user.setNickName(user.getEmail());
                user.setEmail(user.getEmail());
                String salt = CryptoUtils.getSalt();//盐值
//              随机密码
                String randomString = RandomStringGenerator.getRandomString(20);
                String password = randomString.toUpperCase();
                String hashPassword = CryptoUtils.getHash(password, salt);//加密的密码
                user.setSalt(salt);
                user.setPassword(hashPassword);
                user.setRole("ROLE_ADMIN");
                if (userService.userAdd(user)) {
                    log.debug("新建管理员成功");
                } else {
                    log.debug("新建管理员失败，dao层错误");
                    Msg msg = new Msg("failure", "新建管理员失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                }
                Token token = new Token();
                token.setToken(RandomStringGenerator.getRandomString(50));
                Date dt = new Date();
                long nowSec = dt.getTime() / 1000;
                Date expireTime = new Date((nowSec + 36000) * 1000);
                Date addTime = new Date(nowSec * 1000);
                token.setExpireTime(expireTime);
                token.setAddTime(addTime);
                token.setMaster("ACTIVE_ACCOUNT");
                user = userService.userFindByEmail(user);
                token.setRemark(String.valueOf(user.getId()));
                if (tokenService.isDuplicateToken(token)) {
                    log.debug("新建管理员失败，token重复");
                    Msg msg = new Msg("failure", "新建管理员失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                } else {
                    tokenService.tokenAdd(token);
                    SendEmail sendEmail = new SendEmail();
                    String link = "http://" + HOSTNAME_AND_PORT + "/pass/signIn/active/" + user.getId() + "_" + token.getToken();
                    if (sendEmail.sendMailActiveAccount(email, link, password)) {
                        log.debug("邮件发送成功");
                        Msg msg = new Msg("success", "新建管理员成功，请让管理员按照邮件激活账户。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    } else {
                        log.debug("邮件发送失败");
                        Msg msg = new Msg("failure", "新建管理员失败，未知错误。");
                        model.addAttribute("msg", msg);
                        return "admin/admin_index";
                    }
                }
            }
        } catch (Exception e) {
            log.debug("新建管理员失败，未知错误");
            e.printStackTrace();
            return "redirect:" + REGISTER_PAGE_UNKNOWN;
        }
    }

    @RequestMapping(value = "/admin/updateStudentClazz", method = RequestMethod.POST)
    public String updateStudentClazz(String uid, String studentsId, String clazzsId, Model model) {
        if ("".equals(uid)) {
            log.debug("修改学生班级失败，uid为空");
            Msg msg = new Msg("failure", "修改学生班级失败，未知错误。");
            model.addAttribute("msg", msg);
            return "admin/admin_index";
        }
        if ("".equals(studentsId)) {
            log.debug("修改学生班级失败，studentsName为空");
            Msg msg = new Msg("failure", "修改学生班级失败，未选择学生。");
            model.addAttribute("msg", msg);
            return "admin/admin_index";
        }
        if ("".equals(clazzsId)) {
            log.debug("修改学生班级失败，clazzsId为空");
            Msg msg = new Msg("failure", "修改学生班级失败，未选择班级。");
            model.addAttribute("msg", msg);
            return "admin/admin_index";
        } else {
            log.debug("修改学生班级");
            log.debug("uid: " + uid + ",studentsId: " + studentsId + ",clazzsId: " + clazzsId);
            String[] studentsIdArray = studentsId.split(",");
            for (String studentId : studentsIdArray) {
                User user = new User();
                user.setId(Integer.parseInt(studentId));
                user.setClazz(clazzsId);
                try {
                    if (userService.userUpdateById(user)) {
                        log.debug("修改学生班级成功，学生id：" + studentId + "，课程id：" + clazzsId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.debug("修改学生班级失败");
                    Msg msg = new Msg("failure", "修改学生班级失败，未知错误。");
                    model.addAttribute("msg", msg);
                    return "admin/admin_index";
                }
            }
            log.debug("修改学生班级成功");
            Msg msg = new Msg("success", "修改学生班级成功。");
            model.addAttribute("msg", msg);
            return "admin/admin_index";
        }

    }


    @RequestMapping(value = "/admin/updateBasic", method = RequestMethod.POST)
    public String adminUpdateBasic(User user, Model model, HttpSession session, HttpServletRequest request) {
        Date date = new Date();
        user.setUpdateTime(date);
        User oldUser = userService.userFindById(user);
        log.info(user.toString());
        if (null == user.getEmail() && "".equals(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱为空。");
            return "admin/admin_index";
        } else if (null == user.getPhoneNumber() && "".equals(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号为空。");
            return "admin/admin_index";
        } else if (null == user.getNickName() && "".equals(user.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，昵称为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，昵称为空。");
            return "admin/admin_index";
        } else if (!Validator.isEmail(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱格式不正确。");
            return "admin/admin_index";
        } else if (!Validator.isMobile(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号格式不正确。");
            return "admin/admin_index";
        } else if (userService.isDuplicatePhoneNumber(user) && !user.getPhoneNumber().equals(oldUser.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号已存在。");
            return "admin/admin_index";
        } else if (userService.isDuplicateWxId(user) && !user.getWxId().equals(oldUser.getWxId())) {
            Msg msg = new Msg("failure", "更新基本信息失败，微信号已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，微信号已存在。");
            return "admin/admin_index";
        } else if (userService.isDuplicateEmail(user) && !user.getEmail().equals(oldUser.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱已存在。");
            return "admin/admin_index";
        } else if (userService.isDuplicateNickName(user) && !user.getNickName().equals(oldUser.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，昵称已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，昵称已存在。");
            return "admin/admin_index";
        } else if (!userService.userUpdateById(user)) {
            Msg msg = new Msg("failure", "更新基本信息失败，未知错误。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，未知错误。");
            return "admin/admin_index";
        } else {
            Msg msg = new Msg("success", "更新基本信息成功。");
            model.addAttribute("msg", msg);
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新基本信息成功。");
            return "admin/admin_index";
        }
    }


    @RequestMapping(value = "/student/updateBasic", method = RequestMethod.POST)
    public String studentUpdateBasic(User user, Model model, HttpSession session, HttpServletRequest request, HttpResponse response) {
        Date date = new Date();
        user.setUpdateTime(date);
        User oldUser = userService.userFindById(user);
        log.info(user.toString());
        if (null == user.getEmail() && "".equals(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱为空。");
            return "stu/stu_index";
        } else if (null == user.getPhoneNumber() && "".equals(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号为空。");
            return "stu/stu_index";
        } else if (null == user.getNickName() && "".equals(user.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，姓名为空。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，姓名为空。");
            return "stu/stu_index";
        } else if (!Validator.isEmail(user.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱格式不正确。");
            return "stu/stu_index";
        } else if (!Validator.isMobile(user.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号格式不正确。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号格式不正确。");
            return "stu/stu_index";
        } else if (userService.isDuplicatePhoneNumber(user) && !user.getPhoneNumber().equals(oldUser.getPhoneNumber())) {
            Msg msg = new Msg("failure", "更新基本信息失败，手机号已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，手机号已存在。");
            return "stu/stu_index";
        } else if (userService.isDuplicateWxId(user) && !user.getWxId().equals(oldUser.getWxId())) {
            Msg msg = new Msg("failure", "更新基本信息失败，微信号已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，微信号已存在。");
            return "stu/stu_index";
        } else if (userService.isDuplicateEmail(user) && !user.getEmail().equals(oldUser.getEmail())) {
            Msg msg = new Msg("failure", "更新基本信息失败，邮箱已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，邮箱已存在。");
            return "stu/stu_index";
        } else if (userService.isDuplicateNickName(user) && !user.getNickName().equals(oldUser.getNickName())) {
            Msg msg = new Msg("failure", "更新基本信息失败，昵称已存在。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，昵称已存在。");
            return "stu/stu_index";
        } else if (!userService.userUpdateById(user)) {
            Msg msg = new Msg("failure", "更新基本信息失败，未知错误。");
            model.addAttribute("msg", msg);
            log.debug("更新基本信息失败，未知错误。");
            return "stu/stu_index";
        } else {
            Msg msg = new Msg("success", "更新基本信息成功。");
            model.addAttribute("msg", msg);
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新基本信息成功。");
            return "stu/stu_index";
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
            return "stu/stu_index";
        } else if (null == oldPassword && "".equals(oldPassword)) {
            Msg msg = new Msg("failure", "更新密码失败，旧密码为空。");
            model.addAttribute("msg", msg);
            log.debug("更新密码失败，旧密码为空。");
            return "stu/stu_index";
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
            return "stu/stu_index";
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
            return "stu/stu_index";
        } else {
            Msg msg = new Msg("success", "更新密码成功。");
            model.addAttribute("msg", msg);
            session.setAttribute("user", userService.userFindById(user));
            log.debug("更新密码成功。");
            return "stu/stu_index";
        }
    }

}
