package com.gwm.one.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gwm.one.enums.ResponseStatus.RESPONSE_ACCESS_DENIED_ERROR;

/**
 * @author liangzheng
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap();
        map.put("errorCode", RESPONSE_ACCESS_DENIED_ERROR.code);
        map.put("message", accessDeniedException.getMessage());
        //map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}