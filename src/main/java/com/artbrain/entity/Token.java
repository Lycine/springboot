package com.artbrain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by hongyu on 2017/4/30.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {
    private int id;
    private String token;
    private String master;
    private String remark;
    private Date addTime;
    private Date expireTime;

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", master='" + master + '\'' +
                ", remark='" + remark + '\'' +
                ", addTime=" + addTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
