package com.cloud.gateway.filter;

import com.cloud.common.utils.RedisUtils;
import com.cloud.gateway.config.SpringContextHolder;
import com.cloud.gateway.config.UserInterceptor;
import com.cloud.model.common.Response;
import com.cloud.utils.JsonUtils;
import com.cloud.common.utils.IPUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.cloud.common.constants.SysConstants.PAST;
import static com.cloud.enums.ResponseStatus.RESPONSE_LOGOUT_SIGNAL_ERROR;
import static com.cloud.gateway.config.UserInterceptor.USER_CODE;

/**
 * 过滤uri<br>
 * 该类uri不需要登陆，但又不允许外网通过网关调用，只允许微服务间在内网调用，<br>
 * 为了方便拦截此场景的uri，我们自己约定一个规范，及uri中含有-anon/internal<br>
 * 如在oauth登陆的时候用到根据username查询用户，<br>
 * 用户系统提供的查询接口/users-anon/internal肯定不能做登录拦截，而该接口也不能对外网暴露<br>
 * 如果有此类场景的uri，请用这种命名格式，
 *
 * @author lz
 */
@Component
@Slf4j
public class UniUserFilter extends ZuulFilter {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public Object run() {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            String authentication = request.getHeader("Authorization");

            log.info(String.format("(%s) ip为 %s 收到 %s 请求 %s  %s ", authentication,
                    IPUtil.getIpAddr(request), request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getSession().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        boolean match = PatternMatchUtils.simpleMatch(new String[]{"*-anon/internal*","/app-anon/**","*-anon/codes"}, request.getRequestURI());
        if (match){
            return true;
        }
        String authentication = request.getHeader("Authorization");
        authentication = authentication.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
        //针对多个用户登陆暂时做的处理
        String my = request.getHeader("My");
        if (my != null) {
            String past = (String) redisUtils.get(PAST + my);
            if (past != null && past.equals(authentication)) {
                redisUtils.del(PAST + my);
                sendResponse(requestContext);
                return false;
            }else if (past == null){
                String tok = (String) redisUtils.get(USER_CODE + my);
                String[] split = tok.split("@");
                if (split != null && split.length == 4) {
                    if (!split[3].equals(authentication)){
                        redisUtils.set(PAST + my, split[3]);
                        sendResponse(requestContext);
                    }
                }
            }
        }

        Cookie[] cookies = request.getCookies();
        if (UserInterceptor.getCookies(redisUtils, cookies)) {
            return true;
        }
        return false;
    }

    private void sendResponse(RequestContext requestContext) {
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Response result = new Response();
        result.setMessage(RESPONSE_LOGOUT_SIGNAL_ERROR.message);
        result.setErrorCode(RESPONSE_LOGOUT_SIGNAL_ERROR.code);
        try {
            requestContext.getResponse().getWriter().write(JsonUtils.toJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

}
