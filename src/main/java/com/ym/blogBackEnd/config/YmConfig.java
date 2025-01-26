package com.ym.blogBackEnd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: YmConfig
 * @Author YunMao
 * @Package com.ym.blogbackend.config
 * @Date 2025/1/13 18:31
 * @description: 云猫配置文件变量读取
 */
@Data
@Component
@ConfigurationProperties(prefix = "ym")
public class YmConfig {

    /**
     * 加盐 key
     */
    private String encrypt;

    /**
     * 默认 密码
     */
    private String defaultPassword;

    /**
     * 账号 最小长度
     */
    private Integer accountLength;

    /**
     * 密码 最小长度
     */
    private Integer passwordLength;

    /**
     * 发送邮件
     */
    private String emailFrom;
}
