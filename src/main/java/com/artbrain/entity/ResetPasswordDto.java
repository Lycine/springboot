package com.artbrain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hongyu on 2017/5/1.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordDto {
    private String uid;
    private String token;
    private String password;

    @Override
    public String toString() {
        return "ResetPasswordDto{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
