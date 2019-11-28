package com.gwm.one.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lz
 * Date: 9/2/2019
 * Time: 2:02 PM
 */
public class Messages {
    public static Map<String, String> msgMap = new HashMap<>();
    static {
        msgMap.put("Bad credentials", "密码错误");
        msgMap.put("Unsupported grant type", "不支持的类型");
        msgMap.put("Invalid scope", "scope不支持的类型");
        msgMap.put("Full authentication is required to access this resource", "clientId或是clientSecret不正确");
    }
    public static final String APP = "app";
    public static final String OAUTH = "oauth";
}
