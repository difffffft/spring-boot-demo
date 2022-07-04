package com.example.advice;

import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理通知类
 */
@Slf4j
@RestControllerAdvice
public class AppExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R Exception(Exception e) {
        log.info(e.getMessage());
        return R.error(e.getMessage());
    }

}