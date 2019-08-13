package com.cloud.common.exception;

import lombok.Data;

/**
 * 自定义异常处理
 * @author lz
 * @date 2018/8/14
 */
@Data
public class GenericBusinessException extends Exception {
    //错误状态码
    int code;

    //响应状态码  对应http response status code
    int responseStatus = 500;

    public GenericBusinessException(String message) {
        super(message);
    }

    public GenericBusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GenericBusinessException(int code, int responseStatus, String message) {
        super(message);
        this.code = code;
        this.responseStatus = responseStatus;
    }


    @Override
    public String toString() {
        return "{" + "\"code\":" + code + ",\"message\":\"" + getMessage() + "\"," + "\"responseStatus\":" + responseStatus + '}';
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }
}
