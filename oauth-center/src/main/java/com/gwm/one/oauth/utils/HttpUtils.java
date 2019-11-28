package com.gwm.one.oauth.utils;

import com.gwm.one.oauth.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lz
 * @create 2018/11/5 16:34
 * @description
 * @modify
 */
public class HttpUtils {

    public static void writerError(BaseResponse bs, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(bs.getStatus());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), bs);
    }

}
