package com.artbrain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by hongyu on 2017/5/2.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Bulletin {
    private int id;
    private String title;
    private String content;
    private String from;
    private String to;
    private Date addTime;

    @Override
    public String toString() {
        return "Bulletin{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
