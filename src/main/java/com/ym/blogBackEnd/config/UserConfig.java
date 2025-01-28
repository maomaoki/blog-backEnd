package com.ym.blogBackEnd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: UserConfig
 * @Author YunMao
 * @Package com.ym.blogBackEnd.config
 * @Date 2025/1/28 22:27
 * @description: 用户配置项
 */
@Data
@Component
@ConfigurationProperties(prefix = "ym.user")
public class UserConfig {

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
}
