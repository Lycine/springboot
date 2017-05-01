package com.artbrain.util;

/**
 * 全局配置类
 */
public class Global {
    public static final String HOSTNAME_AND_PORT = "localhost:8082";
    /**
     * 登录失败失败次数阈值
     */
    public static final int SIGNIN_FAILURE_THRESHOLD = 5;

    /**
     * 登录失败
     * 停用
     * CODE
     */
    public static final int SIGNIN_FAILURE_USERSTOP_CODE = 101;

    /**
     * 登录失败
     * 已逻辑删除
     * CODE
     */
    public static final int SIGNIN_FAILURE_USERDELETE_CODE = 102;

    /**
     * 登录失败
     * 密码错误
     * CODE
     */
    public static final int SIGNIN_FAILURE_WRONGPASSWORD_CODE = 103;

    /**
     * 登录失败
     * 未知错误
     * CODE
     */
    public static final int SIGNIN_FAILURE_UNKNOWN_CODE = 104;

    /**
     * 登录失败
     * 未找到用户
     * CODE
     */
    public static final int SIGNIN_FAILURE_USERNOTFOUND_CODE = 105;

    /**
     * 处理登录
     * CONTROLLER
     */
    public static final String SIGNIN_PROCESSIN_CONTROLLER = "/pass/signIn";

    /**
     * 处理激活账户
     * CONTROLLER
     */
    public static final String ACTIVE_ACCOUNT_PROCESSIN_CONTROLLER = "/pass/signIn/active";

    /**
     * 处理注销
     * CONTROLLER
     */
    public static final String LOGOUT_PROCESSIN_CONTROLLER = "/pass/logout";

    /**
     * 处理注册
     * CONTROLLER
     */
    public static final String REGISTER_PROCESSIN_CONTROLLER = "/pass/register";

    /**
     * 处理忘记密码发送链接邮件
     * CONTROLLER
     */
    public static final String FORGET_PASSWORD_SEND_EMAIL_CONTROLLER = "/pass/forgetPassword";

    /**
     * 处理忘记密码链接邮件
     * CONTROLLER
     */
    public static final String FORGET_PASSWORD_RESET_PASSWORD_BY_LINK_CONTROLLER = "/pass/forgetPassword/resetByLink";

    /**
     * 注销成功
     * CONTROLLER
     * 注销暂时没有需要的操作，所以没有对应的controller（以后可以添加注销时间，本次在线时常等）
     */
    public static final String LOGOUT_SUCCESS_CONTROLLER = "/LOGOUT/SUCCESS/CONTROLLER/PATH/NOT/SET";

    /**
     * 登录成功
     * CONTROLLER
     */
    public static final String SIGNIN_SUCCESS_CONTROLLER = "/pass/signInSuccess";


    /**
     * 登录失败
     * 未知错误
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_CONTROLLER = "/pass/signInFailure/";

    /**
     * 登录失败
     * 未知错误
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_UNKNOWN_CONTROLLER = SIGNIN_FAILURE_CONTROLLER + SIGNIN_FAILURE_UNKNOWN_CODE + "/0";

    /**
     * 登录失败
     * 密码错误
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_WRONGPASSWORD_CONTROLLER = SIGNIN_FAILURE_CONTROLLER + SIGNIN_FAILURE_WRONGPASSWORD_CODE + "/";

    /**
     * 登录失败
     * 用户已停用
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_USERSTOP_CONTROLLER = SIGNIN_FAILURE_CONTROLLER + SIGNIN_FAILURE_USERSTOP_CODE + "/";

    /**
     * 登录失败
     * 用户已逻辑删除
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_USERDELETE_CONTROLLER = SIGNIN_FAILURE_CONTROLLER + SIGNIN_FAILURE_USERDELETE_CODE + "/";

    /**
     * 登录失败
     * 用户未找到
     * CONTROLLER
     */
    public static final String SIGNIN_FAILURE_USERNOTFOUND_CONTROLLER = SIGNIN_FAILURE_CONTROLLER + SIGNIN_FAILURE_USERNOTFOUND_CODE + "/";


    /**
     * 登录页面
     * （注销成功）
     * html模版
     */
    public static final String LOGOUT_SUCCESS_URL = "/pass?signIn&logoutSuccess";

    /**
     * 登录页面
     * （普通）
     * html模版
     */
    public static final String SIGNIN_PAGE_SIGNINFIRST = "/pass?signIn&signInFirst";

    /**
     * 登录页面
     * （访问其他页面，必须先登录）
     * html模版
     */
    public static final String SIGNIN_PAGE = "/pass?signIn";

    /**
     * 登录页面
     * （登录失败，密码用户不匹配，一般错误）
     * html模版
     */
    public static final String SIGNIN_PAGE_NORMALFAILURE = "/pass?signIn&normalFailure&count=";

    /**
     * 登录页面
     * （激活失败，链接错误）
     * html模版
     */
    public static final String SIGNIN_PAGE_ACTIVE_ERRORLINK = "/pass?signIn&active&errorLink";

    /**
     * 登录页面
     * （激活成功）
     * html模版
     */
    public static final String SIGNIN_PAGE_ACTIVE_SUCCESS = "/pass?signIn&active&success";

    /**
     * 登录页面
     * （激活失败，未知错误）
     * html模版
     */
    public static final String SIGNIN_PAGE_ACTIVE_UNKNOWN = "/pass?signIn&active&unknown";


    /**
     * 登录页面
     * （登录失败，没找到用户）
     * html模版
     */
    public static final String SIGNIN_PAGE_USERNOTFOUND = "/pass?signIn&normalFailure";

    /**
     * 登录页面
     * （登录失败，当前用户停用）
     * html模版
     */
    public static final String SIGNIN_PAGE_STOP = "/pass?signIn&locked";

    /**
     * 登录页面
     * （登录失败，当前用户已被删除）
     * html模版
     */
    public static final String SIGNIN_PAGE_DELETE = "/pass?signIn&signInLock&deleteed";

    /**
     * 登录页面
     * （登录失败，未知错误）
     * html模版
     */
    public static final String SIGNIN_PAGE_ABNORMALFAILURE = "/pass?signIn&unknownError";

    /**
     * 注册页面
     * html模版
     */
    public static final String REGISTER_PAGE = "/pass?register";

    /**
     * 注册页面
     * （成功）
     * html模版
     */
    public static final String REGISTER_PAGE_SUCCESS = "/pass?register&success";

    /**
     * 注册页面
     * （成功）
     * html模版
     */
    public static final String REGISTER_PAGE_SENDFAILURE = "/pass?register&sendFailure";

    /**
     * 注册页面
     * （注册失败，重复的邮箱）
     * html模版
     */
    public static final String REGISTER_PAGE_DUPLICATEEMAIL = "/pass?register&duplicatedEmail";

    /**
     * 注册页面
     * （注册失败，错误的邮箱格式或密码格式。一般不会出现这个错误，除非前端验证有问题）
     * html模版
     */
    public static final String REGISTER_PAGE_WRONGFORMAT = "/pass?register&wrongFormat";

    /**
     * 注册页面
     * （注册失败，未知错误）
     * html模版
     */
    public static final String REGISTER_PAGE_UNKNOWN = "/pass?register&unknownError";

    /**
     * 忘记密码页面
     * （成功，已发送邮件）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_SUCCESS = "/pass?forgetPassword&success";

    /**
     * 忘记密码页面
     * （忘记密码失败，邮箱为空。一般不会出现这个错误，除非前端验证有问题）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_EMPTYMAIL = "/pass?forgetPassword&emptyMail";

    /**
     * 忘记密码页面
     * （忘记密码失败，错误的邮箱格式。一般不会出现这个错误，除非前端验证有问题）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_WRONGFORMAT = "/pass?forgetPassword&wrongFormat";

    /**
     * 忘记密码页面
     * （忘记密码失败，错误的邮箱格式。一般不会出现这个错误，除非前端验证有问题）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_SENDFAILURE = "/pass?forgetPassword&sendFailure";


    /**
     * 忘记密码页面
     * （忘记密码失败，没有这个账户）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_USERNOTFOUND = "/pass?forgetPassword&normalFailure";

    /**
     * 忘记密码页面
     * （忘记密码失败，未知错误）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_UNKNOWN = "/pass?forgetPassword&unknownError";

    /**
     * 忘记密码页面
     * （忘记密码失败，未知错误）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_ERRORLINK = "/pass?forgetPassword&errorLink";

    /**
     * 忘记密码页面
     * （忘记密码失败，token超时）
     * html模版
     */
    public static final String FORGET_PASSWORD_PAGE_TOKEN_EXPIRE = "/pass?forgetPassword&tokenExpire";

}
