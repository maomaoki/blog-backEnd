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
     * 成功 就 抛异常
     *
     * @param condition
     * @param runtimeException
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
