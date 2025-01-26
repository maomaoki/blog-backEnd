package com.ym.blogBackEnd.exception;

import lombok.Getter;

/**
 * 自定义 异常错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "ok"),
    ERROR_PARAMS(40000, "请求参数错误"),
    ERROR_NOT_LOGIN_ERROR(40100, "未登录"),
    ERROR_NO_AUTH_ERROR(40101, "无权限"),
    ERROR_NOT_FOUND_ERROR(40400, "请求数据不存在"),
    ERROR_FORBIDDEN_ERROR(40300, "禁止访问"),
    ERROR_SYSTEM_ERROR(50000, "系统内部异常"),
    ERROR_OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
