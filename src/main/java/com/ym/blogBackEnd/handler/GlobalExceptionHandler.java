package com.ym.blogBackEnd.handler;

import com.ym.blogBackEnd.exception.CustomizeException;
import com.ym.blogBackEnd.exception.ErrorCode;
import com.ym.blogBackEnd.common.response.BaseResponse;
import com.ym.blogBackEnd.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomizeException.class)
    public BaseResponse<?> businessExceptionHandler(CustomizeException e) {
        log.error("CustomizeException", e);
        return ResultUtils.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.ERROR_SYSTEM_ERROR, "系统错误");
    }
}

