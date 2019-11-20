package com.gwm.one.utils;

import com.gwm.one.enums.ResponseStatus;

import java.util.Map;

/**
 * zuul 工具类
 */
public class ZuulUtils {
    /**
     * token生成后也要加入errorCode
     * @param tokenInfo
     * @param responseStatus
     */
    public static void initZuulResponseForCode(Map<String, Object> tokenInfo, ResponseStatus responseStatus){
        if (tokenInfo.get("errorCode") == null) {
            if (tokenInfo.get("access_token") != null) {
                tokenInfo.put("errorCode", responseStatus.code);
                tokenInfo.put("message", responseStatus.message);
            }
        }
    }
}
