package com.cloud.common.enums;

/**
 * 自定义响应状态信息
 */
public enum ResponseStatus {
    RESPONSE_SUCCESS(10000, "成功"),
    RESPONSE_VALIDATE_ERROR(10003, "校验失败"),
    RESPONSE_OPERATION_ERROR(10004, "操作错误"),
    RESPONSE_VERIFICATIONCODE_ERROR(10005, "验证码错误"),
    RESPONSE_MOBILE_EXIST(10006, "手机号已存在"),
    RESPONSE_ZUUL_ERROR(90000, "zuul调用微服务失败，请查看是否微服务启动"),
    RESPONSE_MICRO_SERVICE_ERROR(401, "不能直接调用微服务!!"),
    RESPONSE_PHONE_ERROR(10007, "手机格式不对"),
    RESPONSE_PHONE_PAY_ERROR(10448, "非本账户绑定号码!"),
    RESPONSE_NOREGISTER_ERROR(10008, "手机号未注册"),
    RESPONSE_PHONE_DISABLE_ERROR(10009, "手机号被禁用"),
    RESPONSE_VERIFICATION_CODE__ERROR(10012, "获取验证码失败"),
    RESPONSE_VERIFICATION_CODE_DISABLE_ERROR(10013, "验证码失效"),
    RESPONSE_BINDING_PHONE_FALL_ERROR(10014, "用户绑定手机号失败"),
    RESPONSE_PHP_ERROR(8899, "未知错误!"),
    RESPONSE_INTERNAL_ERROR(9999, "服务器超时"),
    RESPONSE_STORE_SUCCEED(10000, "成功!"),
    RESPONSE_STORE_ERROR(44444, "失败!");

    public final int code;
    public final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}