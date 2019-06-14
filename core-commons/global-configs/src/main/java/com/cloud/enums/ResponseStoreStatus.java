package com.cloud.enums;

/**
 * 商城通用返回
 */
public enum ResponseStoreStatus {
    RESPONSE_STORE_SUCCEED(10000, "成功!"),
    RESPONSE_STORE_DATA_NULL(10002,"没有查询到数据!"),
    RESPONSE_STORE_DATA_MORE(10003,"没有更多数据!"),
    RESPONSE_STORE_NOT_EMPTY_ERROR(10004,"参数不能为空!"),

    //=======================================秒杀相关==========================================
    RESPONSE_STORE_NO_SECKILL(20004, "秒杀都结束了"),





    RESPONSE_STORE_ERROR(44444, "服务器连接超时!");

    public final int code;
    public final String message;

    ResponseStoreStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}