package com.ym.blogBackEnd.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户 邮箱注册 请求 类
 *
 * @author YunMao
 */
@Data
public class UserRegisterDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;


    /**
     * 密码
     */
    private String password;


    /**
     * 确认 密码
     */
    private String confirmPassword;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String code;

}
