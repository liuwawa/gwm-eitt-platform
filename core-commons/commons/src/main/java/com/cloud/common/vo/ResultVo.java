package com.cloud.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应数据(结果)最外层对象
 *
 * @author lz
 * @date 2018/10/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {

    // 状态码
    private Integer code;
    // 提示信息
    private String msg;
    // 响应数据
    private T data;
}