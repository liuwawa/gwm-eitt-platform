package com.cloud.common.vo;

import com.cloud.common.enums.ResponseStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 响应数据最外层对象
 */
@Data
public class Response implements Serializable {
    private static final long serialVersionUID = 8348742053960354809L;
    // http 状态码
    private int errorCode;
    // 返回信息
    private String errorMsg;
    //jwt token
    private String token;
    //app请求的时候传的是dto,里面是encryptedToken,为了统一，返回的Response的字段也叫encryptedToken
    private String encryptedToken;
    // 返回的数据
    private Object data;

    /**
     * @param integer 实际影响的行数
     * @param expect  期待影响的行数
     * @return
     * @desc 增删改表返回的影响的行数的处理
     */
    public static Response processUpdateDatabase(Integer integer, Integer expect) {
        Response response = new Response();
        if (expect.equals(integer)) {
            response.setErrorCode(ResponseStatus.RESPONSE_SUCCESS.code);
            response.setErrorMsg(ResponseStatus.RESPONSE_SUCCESS.message);
        } else {
            response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
            response.setErrorMsg(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
        }
        return response;
    }

    public static Response createResponse() {
        Response response = new Response();
        response.setErrorCode(ResponseStatus.RESPONSE_SUCCESS.code);
        response.setErrorMsg(ResponseStatus.RESPONSE_SUCCESS.message);
        return response;
    }
}
