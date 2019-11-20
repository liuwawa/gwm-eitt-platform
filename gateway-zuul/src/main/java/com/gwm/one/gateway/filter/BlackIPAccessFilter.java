package com.gwm.one.gateway.filter;

import com.gwm.one.common.utils.IPUtil;
import com.gwm.one.gateway.feign.BackendClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 黑名单IP拦截<br>
 * 定时把黑名单ip列表同步到网关层,
 *
 * @author lz
 */
@Component
public class BlackIPAccessFilter extends ZuulFilter {

    /**
     * 黑名单列表
     */
    private Set<String> blackIPs = new HashSet<>();
    @Autowired
    private BackendClient backendClient;

    @Override
    public boolean shouldFilter() {
        if (blackIPs.isEmpty()) {
            syncBlackIPList();
        }

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String ip = IPUtil.getIpAddr(request);
// 判断ip是否在黑名单列表里
        return blackIPs.contains(ip);
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        requestContext.setResponseBody("black ip");
        requestContext.setSendZuulResponse(false);

        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 定时同步黑名单IP
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @Async
    public void syncBlackIPList() {
        try {
            Set<String> list = backendClient.findAllBlackIPs(Collections.emptyMap());
            blackIPs = list;
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * 获取请求的真实ip
     *
     * @param request
     * @return
     */
//    public static String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }

}
