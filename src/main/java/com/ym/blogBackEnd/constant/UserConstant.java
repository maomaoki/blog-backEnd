package com.ym.blogBackEnd.constant;

/**
 * 用户 常量
 */
public interface UserConstant {


    /**
     *  用户 存放 登录态的 key
     */
    String USER_LOGIN_STATE = "userLoginState";



    // 用户状态
    /**
     * 用户 账号 状态 正常
     */
    Integer USER_STATUS_IS_ACTIVE = 0;

    /**
     * 用户 账号 状态 禁止
     */
    Integer USER_STATUS_IS_NOT_ACTIVE = 1;


    // 用户权限
    /**
     * 用户 权限 普通用户
     */
    String USER_ROLE_USER = "user";

    /**
     * 用户 权限 管理员
     */
    String USER_ROLE_ADMIN = "admin";

    /**
     * 用户 权限 超级管理员
     */
    String USER_ROLE_BOSS = "boss";

    /**
     * 用户 权限 黑名单用户
     */
    String USER_ROLE_BLACK_USER = "blackUser";


    // 用户注册来源
    /**
     * 用户 注册来源 账号
     */
    String USER_REGISTER_FROM_ACCOUNT = "account";

    /**
     * 用户 注册来源 qq
     */
    String USER_REGISTER_FROM_QQ = "qq";

    /**
     * 用户 注册来源 微信
     */
    String USER_REGISTER_FROM_WECHAT = "wechat";

    /**
     * 用户 注册来源 邮箱
     */
    String USER_REGISTER_FROM_EMAIL = "email";


    /**
     * 用户 注册来源 管理员添加
     */
    String USER_REGISTER_FROM_ADMIN = "admin";


}
