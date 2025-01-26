package com.ym.blogBackEnd.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Title: UserLoginDto
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user
 * @Date 2025/1/13 17:49
 * @description: 登录 请求 类
 */
@Data
public class UserLoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;


    /**
     * 密码
     */
    private String password;


}
