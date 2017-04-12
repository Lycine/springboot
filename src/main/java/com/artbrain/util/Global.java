package com.artbrain.util;


/**
 * 全局配置类
 */
public class Global {
    /**
     * 登录失败次数阈值
     */
    public static final int SIGNIN_FAILURE_THRESHOLD = 5;

    /**
     * 注销成功url
     */
    public static final String LOGOUT_SUCCESS_URL = "/pass?signIn&logoutSuccess";

    /**
     * 登录页面
     */
    public static final String SIGNIN_PAGE = "/pass?signIn&signInFirst";

    /**
     * 处理登录url
     */
    public static final String SIGNIN_PROCESSIN_URL = "/pass/signIn";

    /**
     * 处理注销url
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 处理注销url
     */
    public static final String SIGNIN_SUCCESS_URL = "/pass/SignInSuccess";










    /**
     * 用户停用错误代码
     */
    public static int SECURITY_CODE_USERSTOP = 1;

    /**
     * 用户停用错误代码
     */
    public static int SECURITY_CODE_USERDELETE = 2;

    /**
     * 用户密码错误代码
     */
    public static int SECURITY_CODE_WRONGPASSWORD = 3;

    public static int SIGNUP_FAILURE_UNKWON_CODE = 1;

    public static int SIGNUP_FAILURE_WRONGPASSWORD_CODE = 2;

    public static int SIGNUP_FAILURE_USERSTOP_CODE = 3;

    public static int SIGNUP_FAILURE_USERDELETE_CODE = 4;

    public static String SIGNUP_FAILURE_UNKWON_URL = "/pass/loginFailure/" + SIGNUP_FAILURE_UNKWON_CODE + "/0";

    public static String SIGNUP_FAILURE_WRONGPASSWORD_URL = "/pass/loginFailure/" + SIGNUP_FAILURE_WRONGPASSWORD_CODE + "/";

    public static String SIGNUP_FAILURE_USERSTOP_URL = "/pass/loginFailure/" + SIGNUP_FAILURE_USERSTOP_CODE + "/";

    public static String SIGNUP_FAILURE_USERDELETE_URL = "/pass/loginFailure/" + SIGNUP_FAILURE_USERDELETE_CODE + "/";


}
