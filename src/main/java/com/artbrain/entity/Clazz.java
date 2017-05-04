package com.artbrain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by hongyu on 2017/4/14.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Clazz {
    private int id;
    private String name;
    private String from;
    private Date addTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
