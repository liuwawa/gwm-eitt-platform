package com.gwm.one.oauth.response;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lz
 * @create time  2018/3/30 20:37
 * @description
 * @modify
 * @modify time
 */
public class HttpStatusAndMsg {

    // final 修饰不可继承
    public static final Map<Integer, String> EX_STATUS = new HashMap<>();

    static {

        EX_STATUS.put(200, "Request Success");
        EX_STATUS.put(400, "Bad Request"); // 参数问题
        EX_STATUS.put(401, "NotAuthorization"); // 未认证
        EX_STATUS.put(404, "Not Found"); // 找不到ULR
        EX_STATUS.put(405, "Method Not Allowed"); // 请求方法不正确
        EX_STATUS.put(415, "Unsupported Media Type"); // 不支持Media Type
        EX_STATUS.put(500, "Internal Server Error"); //服务器内部错误
        EX_STATUS.put(1000, "UnKnow Error"); //未知错误

        EX_STATUS.put(1001, "UnKnowException"); // 未知异常
        EX_STATUS.put(1002, "RuntimeException"); // 运行时异常
        EX_STATUS.put(1003, "ClassCastException"); // 类型转换异常
        EX_STATUS.put(1004, "NullPointerException"); // 空指针异常
        EX_STATUS.put(1005, "IOException"); // IO 异常
        EX_STATUS.put(1006, "NoSuchMethodException"); //找不到方法
        EX_STATUS.put(1007, "IndexOutOfBoundsException"); // 数组越界
    }


}
