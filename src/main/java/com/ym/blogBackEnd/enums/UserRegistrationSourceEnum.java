package com.ym.blogBackEnd.enums;

import cn.hutool.core.util.ObjUtil;

/**
 *  用户注册来源 枚举
 */
public enum UserRegistrationSourceEnum {

    ACCOUNT("账号注册", "account"),
    QQ("qq注册", "qq"),
    EMAIL("邮箱注册", "email"),
    ADMIN("管理员添加", "admin");

    private final String text;
    private final String value;

    UserRegistrationSourceEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取 枚举
     * @param value
     * @return
     */
    public static UserRegistrationSourceEnum getByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (UserRegistrationSourceEnum anEnum : UserRegistrationSourceEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }


}
