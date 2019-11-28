package com.gwm.one.common.utils;

import com.gwm.one.common.constants.Messages;
import com.gwm.one.enums.ResponseStatus;
import com.gwm.one.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.gwm.one.enums.ResponseStatus.RESPONSE_CLIENT_ID_ERROR;
import static com.gwm.one.enums.ResponseStatus.RESPONSE_LOGIN_ERROR;

/**
 * Created by lz on 2018/7/10.
 */
public class HttpUtils {
    public static void sendResponse(HttpServletResponse response, String msg, String type) throws ServletException {
        Map map = new HashMap();
        map.put("errorCode", "401");
        switch (type) {
            case Messages.APP:
                map.put("errorCode", RESPONSE_LOGIN_ERROR.code);
                map.put("message", RESPONSE_LOGIN_ERROR.message);
                break;
            case Messages.OAUTH:
                map.put("errorCode", RESPONSE_CLIENT_ID_ERROR.code);
                map.put("message", RESPONSE_CLIENT_ID_ERROR.message);
                break;
            default:
                map.put("message", Messages.msgMap.get(msg));
                break;
        }
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }

    /**
     * 获取用户的错误信息code
     *
     * @param msg
     * @return
     */
    public static ResponseEntity getResponseEntity(String msg) {
        return new ResponseEntity(
                new ErrorResponse(
                        msg,
                        ResponseStatus.getKeyForValue(msg),
                        System.currentTimeMillis()), HttpStatus.OK);
    }
}
