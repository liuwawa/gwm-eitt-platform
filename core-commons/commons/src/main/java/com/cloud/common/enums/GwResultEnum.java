package com.cloud.common.enums;

import lombok.Getter;

/**
 * 通用状态信息
 *
 * @author lz
 * @date 2018/10/15
 */
@Getter
public enum GwResultEnum {

    SUCCESS(200, "成功"),
    ERROR(400, "错误");

    private Integer code;

    private String message;

    GwResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
