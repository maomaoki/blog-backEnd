package com.ym.blogBackEnd.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

/**
 * @Title: EmailCodeTypeEnum
 * @Author YunMao
 * @Package com.ym.blogbackend.enums
 * @Date 2025/1/14 12:54
 * @description: 验证码类型枚举类
 */
@Getter
public enum EmailCodeTypeEnum {

    REGISTER("注册", 0);

    private final String text;
    private final Integer value;

    EmailCodeTypeEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value 获取 枚举
     *
     * @param value
     * @return
     */
    public static EmailCodeTypeEnum getByValue(Integer value) {
        if (ObjUtil.isEmpty(value)) {
            return null;
        }
        for (EmailCodeTypeEnum anEnum : EmailCodeTypeEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
