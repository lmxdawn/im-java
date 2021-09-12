package com.lmxdawn.user.handler;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.exception.JsonException;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 错误回调
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 拦截API异常
    @ExceptionHandler(value = JsonException.class)
    public BaseResponse handlerJsonException(JsonException e) {
        // 返回对应的错误信息
        return ResultVOUtils.error(e.getCode(), e.getMessage());
    }

    // 拦截API异常
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse handlerRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        // 返回对应的错误信息
        return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
    }

}
