package com.cloud.common.entry;

import com.cloud.common.utils.HttpUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            throws ServletException,IOException {
        HttpUtils.sendResponse(response, authException.getMessage(), type);
//        if(isAjaxRequest(request)){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
//        }else{
//            response.sendRedirect("/login");
//        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}