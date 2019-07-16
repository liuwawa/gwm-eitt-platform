package com.cloud.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 异常响应对象
 */
@AllArgsConstructor
@Data
public class ErrorResponse implements Serializable{
    private String message;
    private Integer errorCode;
    private Long timestamp;
}
