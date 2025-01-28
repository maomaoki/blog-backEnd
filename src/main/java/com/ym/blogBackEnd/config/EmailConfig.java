package com.ym.blogBackEnd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: EmailConfig
 * @Author YunMao
 * @Package com.ym.blogBackEnd.config
 * @Date 2025/1/28 22:27
 * @description: 邮箱配置
 */
@Data
@Component
@ConfigurationProperties(value = "ym.email")
public class EmailConfig {
    /**
     * 发送邮件
     */
    private String emailFrom;
}
