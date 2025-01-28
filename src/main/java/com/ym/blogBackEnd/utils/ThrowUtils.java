package com.ym.blogBackEnd.utils;

import com.ym.blogBackEnd.exception.CustomizeException;
import com.ym.blogBackEnd.exception.ErrorCode;

/**
 * 异常 工具
 *
 * @author YunMao
 */
public class ThrowUtils {


    /**
     * 手动抛异常
     *
     * @param condition        结果
     * @param runtimeException true就抛异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }


    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new CustomizeException(errorCode));
    }


    public static void throwIf(boolean condition, ErrorCode errorCode, String msg) {
        throwIf(condition, new CustomizeException(errorCode, msg));
    }

}
