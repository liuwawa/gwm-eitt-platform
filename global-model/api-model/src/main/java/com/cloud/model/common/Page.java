package com.cloud.model.common;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页对象
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Page", description = "分页对象")
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -275582248840137389L;
	@ApiModelProperty(value = "总数")
	private int total;
	@ApiModelProperty(value = "数据")
	private List<T> data;
}
