package com.ym.blogBackEnd.utils;

import com.ym.blogBackEnd.config.YmConfig;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Title: EmailUtils
 * @Author YunMao
 * @Package com.ym.blogbackend.utils
 * @Date 2025/1/14 11:49
 * @description: 邮箱发送工具类
 */
@Component
@Data
public class EmailUtils {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private YmConfig ymConfig;


    /**
     * 异步 发送邮件
     * @param to 接收人
     * @param subject 发送邮件主题
     * @param content 发送 内容
     */
    @Async("taskExecutor")
    public void sendEmail(String to, String subject, String content) {

        // 创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ymConfig.getEmailFrom());
        // 设置收件人
        message.setTo(to);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件内容
        message.setText(content);
        // 发送邮件
        mailSender.send(message);

    }


}
