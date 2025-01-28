package com.ym.blogBackEnd.model.dto.user;

import lombok.Data;

/**
 * @Title: UserEmailCodeDtp
 * @Author YunMao
 * @Package com.ym.blogbackend.model.dto.user
 * @Date 2025/1/14 11:40
 * @description: 用户验证码请求类
 */
@Data
public class UserSendEmailCode {

    /**
     * 请求 账号
     */
    private String account;


    /**
     * 请求 邮箱
     */
    private String email;


    /**
     * 验证码 类型
     */
    private Integer type;

}
