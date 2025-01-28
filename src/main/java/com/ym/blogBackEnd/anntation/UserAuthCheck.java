package com.ym.blogBackEnd.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: UserAuthCheck
 * @Author YunMao
 * @Package com.ym.blogbackend.anntation
 * @Date 2025/1/14 17:50
 * @description: 用户权限校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuthCheck {

    /**
     * 必须 有 某个 角色
     *
     */
    String mustRole() default "";
}
