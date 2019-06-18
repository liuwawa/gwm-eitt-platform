package com.cloud.gateway.filter;

import com.cloud.gateway.utils.IPUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤uri<br>
 * 该类uri不需要登陆，但又不允许外网通过网关调用，只允许微服务间在内网调用，<br>
 * 为了方便拦截此场景的uri，我们自己约定一个规范，及uri中含有-anon/internal<br>
 * 如在oauth登陆的时候用到根据username查询用户，<br>
 * 用户系统提供的查询接口/users-anon/internal肯定不能做登录拦截，而该接口也不能对外网暴露<br>
 * 如果有此类场景的uri，请用这种命名格式，
 * 
 * @author lz
 *
 */
@Component
@Slf4j
public class TestFilter extends ZuulFilter {

	@Override
	public Object run() {
		try {
			RequestContext requestContext = RequestContext.getCurrentContext();
			HttpServletRequest request = requestContext.getRequest();
			String authentication = request.getHeader("Authorization");
			log.info(String.format("(%s) ip为 %s 收到 %s 请求 %s  %s ", authentication,
                    IPUtil.getIpAddress(request), request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getSession().getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
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