package com.cloud.gateway.config;

import com.cloud.common.utils.RedisUtils;
import com.cloud.model.common.Response;
import com.cloud.utils.JsonUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

import static com.cloud.enums.ResponseStatus.RESPONSE_LOGIN_SIGNAL_ERROR;

/**
 * user filter
 * @author user lz
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    public static final String USER_CODE = "userCode|";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        RedisUtils redisUtils = (RedisUtils) SpringContextHolder.getBean("redisUtils");
        Cookie[] cookies = request.getCookies();
        //第一次登录直接忽略
        if (redisUtils.get(USER_CODE + request.getParameter("username")) == null){
            return true;
        }
        //是否允许被其他用户踢掉
        String out = request.getParameter("out");
        if (out != null && "1".equals(out)){
            return true;
        }
        //此用户已经登陆过
        if (getCookies(redisUtils, cookies)) {
            return true;
        }

        Response result = new Response();
        result.setMessage(RESPONSE_LOGIN_SIGNAL_ERROR.message);
        result.setErrorCode(RESPONSE_LOGIN_SIGNAL_ERROR.code);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(JsonUtils.toJson(result));
        return false;
    }

    /**
     * 校验cookie
     * @param redisUtils
     * @param cookies
     * @return
     */
    public static boolean getCookies(RedisUtils redisUtils, Cookie[] cookies) {
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String username = cookie.getValue().split("_")[0];
                    String token = cookie.getValue().split("_")[1];
                    String value = (String) redisUtils.get(USER_CODE + username);
                    String[] valueArr = value.split("_");
                    String cacheToken = valueArr[0];
                    if (token.equals(cacheToken)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}