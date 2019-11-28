package com.gwm.one.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@ApiModel(value = "ResultVo", description = "返回响应类型")
public class ResultVo<T> {

    // 状态码
    @ApiModelProperty(value = "状态码")
    private Integer code;
    // 提示信息
    @ApiModelProperty(value = "提示信息")
    private String msg;
    // 响应数据
    @ApiModelProperty(value = "响应数据")
    private T data;
}
