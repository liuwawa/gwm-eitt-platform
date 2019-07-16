package com.cloud.common.entry;

import com.cloud.common.utils.HttpUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2018/5/24 0024.
 *
 * @author lz
 * @since 1.0
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    private String type;

    public AuthExceptionEntryPoint() {

    }

    public AuthExceptionEntryPoint(String type) {
        this.type = type;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {
        HttpUtils.sendResponse(response, authException.getMessage(), type);
    }
}