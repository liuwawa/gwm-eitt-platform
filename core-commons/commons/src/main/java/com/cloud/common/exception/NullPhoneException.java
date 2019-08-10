package com.cloud.common.exception;

import com.cloud.common.enums.ResultEnum;
import lombok.Getter;


/**
 * 自定义异常对象
 *
 * @author liuek
 * @date 2019/8/10
 */
@Getter
public class NullPhoneException extends RuntimeException {

    private Integer code;

    public NullPhoneException() {

    }

    /**
     * 统一异常处理
     *
     * @param resultEnum 状态枚举
     */
    public NullPhoneException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * 统一异常处理
     *
     * @param code    状态码
     * @param message 提示信息
     */
    public NullPhoneException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
