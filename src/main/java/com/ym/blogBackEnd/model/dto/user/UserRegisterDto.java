package com.ym.blogBackEnd.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户 注册 请求 类
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

}
