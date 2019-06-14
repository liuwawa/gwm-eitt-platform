package com.cloud.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class ErrorResponse implements Serializable{
    private String message;
    private Integer errorCode;
    private Long timestamp;
}
