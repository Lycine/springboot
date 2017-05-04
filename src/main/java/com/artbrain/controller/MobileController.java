package com.artbrain.controller;

import com.artbrain.entity.Clazz;
import com.artbrain.entity.User;
import com.artbrain.service.ClazzService;
import com.artbrain.service.TokenService;
import com.artbrain.service.UserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@CommonsLog
@Controller
public class MobileController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private TokenService tokenService;


    @ResponseBody
    @RequestMapping(value = "/m/clazzs/{uid}/", produces = "application/json;charset=UTF-8")
    public Object getClazzs(@PathVariable("uid") String uid) {
        List<Map<String, Object>> innerMapList = new ArrayList<>();
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user = userService.userFindById(user);
        if ("ROLE_ADMIN".equals(user.getRole())){
            log.debug("ADMIN权限将显示全部班级");
            List<Clazz> clazzList = clazzService.clazzFindAll(0, 0);
            for (Clazz clazz : clazzList) {
                Map<String, Object> innerMap = new HashMap<String, Object>();
                innerMap.put("id", clazz.getId());
                innerMap.put("text", clazz.getName());
                innerMapList.add(innerMap);
            }
            log.debug(innerMapList.toString());
            return innerMapList;
        } else if ("ROLE_TEACHER".equals(user.getRole())){
            log.debug("TEACHER权限将显示部分班级");
            String clazzes = user.getClazz();
            String[] clazzsArray = clazzes.split(",");
            log.debug("显示班级：" + clazzes);
            for (String clazzId : clazzsArray){
                Clazz clazz = new Clazz();
                clazz.setId(Integer.parseInt(clazzId));
                clazz = clazzService.clazzFindById(clazz);
                System.out.println(clazz.toString());
                Map<String, Object> innerMap = new HashMap<String, Object>();
                innerMap.put("id", clazz.getId());
                innerMap.put("text", clazz.getName());
                innerMapList.add(innerMap);
            }
            log.debug(innerMapList.toString());
            return innerMapList;
        } else {
            log.debug("其他权限显示空");
            return innerMapList;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/m/students/{uid}/", produces = "application/json;charset=UTF-8")
    public Object getStudents(@PathVariable("uid") String uid) {
        List<Map<String, Object>> innerMapList = new ArrayList<>();
        User user = new User();
        user.setId(Integer.parseInt(uid));
        user = userService.userFindById(user);
        if ("ROLE_ADMIN".equals(user.getRole())){
            log.debug("ADMIN权限将显示全部学生");
            List<Clazz> clazzList = clazzService.clazzFindAll(0, 0);
            for (Clazz clazz : clazzList) {
                user = new User();
                user.setClazz(String.valueOf(clazz.getId()));
                List<User> userList = userService.userFindByClazzId(user);
                log.debug("userFindByClazzId: " + userList.toString());
                String clazzIds = "";
                for (User u : userList ) {
                    if ("ROLE_STUDENT".equals(u.getRole())){
                        Map<String, Object> innerestMap = new HashMap<>();
                        innerestMap.put("id", u.getId());
                        innerestMap.put("text", u.getNickName());
                        innerMapList.add(innerestMap);
                        StringBuilder sb = new StringBuilder();
                        clazzIds = sb.append(clazzIds).append(u.getId()+",").toString();
                    }
                }
                //"1,3,5,"->"1,3,5"
                if (!"".equals(clazzIds)){
                    log.debug("clazzIds2:"+clazzIds);
                    clazzIds = clazzIds.substring(0,clazzIds.length()-1);
                    log.debug("clazzIds3:"+clazzIds);
                    Map<String, Object> innerMap = new HashMap<>();
                    innerMap.put("id", clazzIds);
                    innerMap.put("text",clazz.getName());
                    innerMapList.add(innerMap);
                }
            }
            Collections.reverse(innerMapList);
            log.debug(innerMapList.toString());
            return innerMapList;
        } else if ("ROLE_TEACHER".equals(user.getRole())){
            log.debug("TEACHER权限将显示部分学生");
            String clazzes = user.getClazz();
            String[] clazzsArray = clazzes.split(",");
            log.debug("显示学生：" + clazzes);
            for (String clazzId : clazzsArray){
                System.out.println("clazzId123: " + clazzId);
                Clazz clazz = new Clazz();
                clazz.setId(Integer.parseInt(clazzId));
                clazz = clazzService.clazzFindById(clazz);
                System.out.println("clazz123: " +clazz.toString());
                user = new User();
                user.setClazz(String.valueOf(clazz.getId()));
                log.debug("user123: " + user.toString());
                List<User> userList = userService.userFindByClazzId(user);
                log.debug("userFindByClazzId: " + userList.toString());

                List<Map<String,Object>> innerestMapList = new ArrayList<>();
                for (User u : userList ) {
                    Map<String, Object> innerestMap = new HashMap<String, Object>();
                    innerestMap.put("id", u.getId());
                    innerestMap.put("text", u.getNickName());
                    innerestMapList.add(innerestMap);
                }
                Map<String, Object> innerMap = new HashMap<String, Object>();
                innerMap.put("id", "");
                innerMap.put("text", clazz.getName());
                innerMap.put("children", innerestMapList);
                log.debug("innerMap: " + innerMap.toString());
                innerMapList.add(innerMap);
            }
            log.debug(innerMapList.toString());
            return innerMapList;
        } else {
            log.debug("其他权限显示空");
            return innerMapList;
        }
    }
}
