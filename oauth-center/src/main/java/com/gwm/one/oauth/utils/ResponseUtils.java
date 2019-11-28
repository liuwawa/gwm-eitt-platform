package com.gwm.one.oauth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static void getResponse(String msg, HttpServletResponse response) throws Exception {
        Map map = new HashMap();
        map.put("errorCode", "401");
        map.put("message", msg);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception ex) {
            throw new ServletException();
        }
    }
}
