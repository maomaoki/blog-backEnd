package com.ym.blogBackEnd.exception;

import lombok.Getter;

/**
 * 自定义 异常
 */
@Getter
public class CustomizeException extends RuntimeException {

    /**
     * 错误码
     */
    private final int errorCode;

    public CustomizeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    public CustomizeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }


    public CustomizeException(ErrorCode errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode.getCode();
    }
}
