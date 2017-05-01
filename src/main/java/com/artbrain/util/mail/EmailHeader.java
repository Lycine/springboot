package com.artbrain.util.mail;

/**
 * Created by hongyu on 16/9/8.
 */
public class EmailHeader
{
    /**
     * 邮件的信息,可以自己进行设置。
     * 为了方便直接全部设置为静态
     */
    //  收件人邮箱
    public  static String TO = "noreply@jozif.org";
    //  发件人邮箱
    public  static String FROM = "noreply@jozif.org";
    //  服务器 比如QQ的可以设置为smtp.qq.com
    public  static String Email_Host = "smtp.qq.com";
    //  发件人名字
    public  static String FROM_NAME = "noreply";
    //  是否需要验证用户名和密码
    public  static boolean validate = true;
}
