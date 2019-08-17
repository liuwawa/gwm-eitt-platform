package com.cloud.common.exception;

import com.cloud.common.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.cloud.common.enums.ResponseStatus.*;

/**
 * 全局异常处理
 *
 * @author lz
 * @date 2018/8/14
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        log.error(ExceptionUtils.getFullStackTrace(e));  // 记录错误信息
        Response response = new Response();
        if (e instanceof AccessDeniedException) {
            throw new ResultException(HASNOPERMISSION_ERROR.code, HASNOPERMISSION_ERROR.message);
        }

        response.setErrorMsg(RESPONSE_INTERNAL_ERROR.message);
        response.setErrorCode(RESPONSE_INTERNAL_ERROR.code);
        return response;
    }

    @ResponseBody
    @ExceptionHandler(GenericBusinessException.class)
    public Response myErrorHandler(GenericBusinessException ex) {
        Response response = new Response();
        response.setErrorCode(RESPONSE_OPERATION_ERROR.code);
        response.setErrorMsg(RESPONSE_OPERATION_ERROR.message);
        return response;
    }
}
