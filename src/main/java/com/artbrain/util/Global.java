package com.artbrain.util;


/**
 * 全局配置类
 */
public class Global {
    /**
     * 登录失败次数阈值
     */
    public static final int signInFailureThreshold = 5;

    public static int getSignInFailureThreshold() {
        return signInFailureThreshold;
    }
}
