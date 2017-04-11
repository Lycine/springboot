package com.artbrain.entity;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String password;
    private String salt;
    private String loginName;
    private String nickName;
    private String email;
    private String phoneNumber;
    private String role;
    private String loginIp;
    private int loginFailureCount;
    private int isDel;
    private int isStop;
    private Date addTime;
    private Date updateTime;
    private Date lastTime;
    
    public User(User user){
        this.id=user.getId();
        this.password=user.getPassword();
        this.salt=user.getSalt();
        this.loginName=user.getLoginName();
        this.nickName=user.getNickName();
        this.email=user.getEmail();
        this.phoneNumber=user.getPhoneNumber();
        this.role=user.getRole();
        this.loginIp=user.getLoginIp();
        this.loginFailureCount=user.getLoginFailureCount();
        this.isDel=user.getIsDel();
        this.isStop=user.getIsStop();
        this.addTime=user.getAddTime();
        this.updateTime=user.getUpdateTime();
        this.lastTime=user.getLastTime();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginFailureCount=" + loginFailureCount +
                ", isDel=" + isDel +
                ", isStop=" + isStop +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", lastTime=" + lastTime +
                '}';
    }
}
