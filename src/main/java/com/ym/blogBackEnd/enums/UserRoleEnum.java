package com.ym.blogBackEnd.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * 用户 权限 枚举
 */
@Getter
public enum UserRoleEnum {

    USER("普通用户", "user"),
    ADMIN("管理员", "admin"),
    BLACK_USER("黑名单用户", "blackUser"),
    BOSS("boss", "boss");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取 枚举
     * @param value
     * @return
     */
    public static UserRoleEnum getByValue(String value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
