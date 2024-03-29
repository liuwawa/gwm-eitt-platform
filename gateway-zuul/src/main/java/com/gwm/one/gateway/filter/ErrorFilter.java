package com.gwm.one.gateway.filter;

import com.gwm.one.model.common.Response;
import com.gwm.one.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author lz
 */
@Component
public class ErrorFilter extends ZuulFilter {
    private final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        //需要在默认的 SendErrorFilter 之前
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        return RequestContext.getCurrentContext().containsKey("throwable");
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            Object e = ctx.get("throwable");

            if (e != null && e instanceof ZuulException) {
                // Remove error code to prevent further error handling in follow up filters
                // 删除该异常信息,不然在下一个过滤器中还会被执行处理
                ctx.remove("throwable");
                initResponse(ctx, HttpStatus.UNAUTHORIZED.value());
                return ctx;

            }
        } catch (Exception ex) {
            log.error("Exception filtering in custom error filter", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    /**
     * 赋值response参数进行app回传
     *  @param context 上下文
     * @param code code
     */
    private void initResponse(RequestContext context, int code) {
        //令zuul过滤该请求，不对其进行路由
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(code);
        Response result = new Response();
        result.setMessage("请登录后重试");
        result.setErrorCode(code);
        log.info(JsonUtils.toJson(result));
        context.setResponseBody(JsonUtils.toJson(result));
    }
}
