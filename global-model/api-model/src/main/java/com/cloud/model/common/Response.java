package com.cloud.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 8348742053960354809L;
    // http 状态码
    private int errorCode;
    // 返回信息
    private String message;
    // 返回的数据
    private Object data;

    public static Response init(){
        Response response = new Response();
        response.setData(null);
        response.setErrorCode(44444);
        response.setMessage("失败!");
        return response;
    }
}
