package com.artbrain.util.mail;

/**
 * Created by hongyu on 16/9/8.
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator{
    private String username="noreply@jozif.org";
    private String password="bzzflngzbwulbigb";

    public MailAuthenticator() {
        super();
    }

    /**
     * 设置验证的用户名和密码
     */
    public MailAuthenticator(String userName , String password) {
        super();
        this.username = userName;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(this.username,this.password);
    }
}
