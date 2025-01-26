package com.ym.blogBackEnd.common.entity;

import lombok.Data;

/**
 * @Title: EmailCodeRedis
 * @Author YunMao
 * @Package com.ym.blogbackend.common.entity
 * @Date 2025/1/14 11:35
 * @description: 邮箱存放redis实体
 */
@Data
public class EmailCodeRedis {

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String code;

    /**
     *  验证码 类型
     */
    private Integer type;

}
