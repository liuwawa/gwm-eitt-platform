package com.gwm.one.common.exception;

import lombok.Getter;

/**
 * 自定义异常对象
 *
 * @author lz
 * @date 2018/8/14
 */
@Getter
public class SmsException extends RuntimeException {

    /**
     * 统一异常处理
     *
     * @param message 提示信息
     */
    public SmsException(String message) {
        super(message);
    }
}
