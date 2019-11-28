package com.gwm.one.hr.personnel.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义全局异常处理(@RestControllerAdvice + @ExceptionHandler)
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> badRequestException(IllegalArgumentException exception) {
        Map<String, Object> data = new HashMap<>();
        data.put("errorCode", HttpStatus.BAD_REQUEST.value());
        data.put("message", exception.getMessage());

        return data;
    }
}
