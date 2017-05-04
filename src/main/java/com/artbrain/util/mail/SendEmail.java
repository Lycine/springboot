package com.artbrain.util.mail;

/**
 * Created by hongyu on 16/9/8.
 */

import lombok.extern.apachecommons.CommonsLog;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@CommonsLog
public class SendEmail {
    public Boolean sendMailActiveAccount(String to, String link, String tempPassword) {
//      设置发件人
        EmailHeader.TO = to;
//      获取系统环境
        Properties prop = new Properties();
        Authenticator auth = null;
        if (EmailHeader.validate) {
//           邮件服务器认证   用户名和密码
            auth = new MailAuthenticator();
        }
//      添加必要的信息
        prop.put("mail.smtp.host", EmailHeader.Email_Host);
        prop.put("mail.smtp.auth", "true");
//      设置对话和邮件服务器进行通讯
        Session session = Session.getDefaultInstance(prop, auth);
//      设置邮件对象
        Message message = new MimeMessage(session);
        try {
//          设置邮件主题
            message.setSubject("激活账户");
//          设置邮件标题
            message.setHeader("Header", EmailBody.Email_Header);
//          设置发送时间
            message.setSentDate(EmailBody.sendDate);
//          设置发信人地址和名字
            Address address = new InternetAddress(EmailHeader.FROM, "ITTAC");
//          把发件人信息添加到信息中
            message.setFrom(address);
//          设置发件人地址
            Address toAddress = new InternetAddress(EmailHeader.TO);
//          设置接收人地址
            message.setRecipient(Message.RecipientType.TO, toAddress);
            BodyPart messageBodyPart = new MimeBodyPart();
            String emailContent = "激活账户链接：\n" + link + "\n初始密码: \n"+tempPassword+"\n有效期10天。\n请勿将链接透露给其他人。本邮件由系统自动发送，请勿直接回复！\n感谢您的访问,祝您使用愉快！";
            messageBodyPart.setText(emailContent);
            log.debug(emailContent);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
//          保存上面添加的信息
            message.saveChanges();
//          发送邮件
            log.debug("开始发送邮件");
            session.setDebug(false);
            Transport.send(message);
            log.debug("发送成功");
            return true;
        } catch (Exception e) {
            log.debug("邮件发送失败");
            e.printStackTrace();
            return false;
        }
    }

    public Boolean sendMailResetPassword(String to, String link) {
//      设置发件人
        EmailHeader.TO = to;
//      获取系统环境
        Properties prop = new Properties();
        Authenticator auth = null;
        if (EmailHeader.validate) {
//           邮件服务器认证   用户名和密码
            auth = new MailAuthenticator();
        }
//      添加必要的信息
        prop.put("mail.smtp.host", EmailHeader.Email_Host);
        prop.put("mail.smtp.auth", "true");
//      设置对话和邮件服务器进行通讯
        Session session = Session.getDefaultInstance(prop, auth);
//      设置邮件对象
        Message message = new MimeMessage(session);
        try {
//          设置邮件主题
            message.setSubject("重置密码");
//          设置邮件标题
            message.setHeader("Header", EmailBody.Email_Header);
//          设置发送时间
            message.setSentDate(EmailBody.sendDate);
//          设置发信人地址和名字
            Address address = new InternetAddress(EmailHeader.FROM, "ITTAC");
//          把发件人信息添加到信息中
            message.setFrom(address);
//          设置发件人地址
            Address toAddress = new InternetAddress(EmailHeader.TO);
//          设置接收人地址
            message.setRecipient(Message.RecipientType.TO, toAddress);
            BodyPart messageBodyPart = new MimeBodyPart();
            String emailContent = "重置密码链接：\n" + link + "\n有效期1天。\n请勿将链接透露给其他人。本邮件由系统自动发送，请勿直接回复！\n感谢您的访问,祝您使用愉快！";
            messageBodyPart.setText(emailContent);
            log.debug(emailContent);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
//          保存上面添加的信息
            message.saveChanges();
//          发送邮件
            log.debug("开始发送邮件");
            session.setDebug(false);
            Transport.send(message);
            log.debug("发送成功");
            return true;
        } catch (Exception e) {
            log.debug("邮件发送失败");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new SendEmail().sendMailResetPassword("petrel2015@foxmail.com", "This is link!");
    }

}