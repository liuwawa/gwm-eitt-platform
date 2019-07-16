package com.cloud.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * 分页返回结果
 * @author wp
 * @date Aug 19, 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult {
	/**
	 * 当前页码
	 */
	private long pageNum;
	/**
	 * 每页数量
	 */
	private long pageSize;
	/**
	 * 记录总数
	 */
	private long totalSize;
	/**
	 * 页码总数
	 */
	private long totalPages;
	/**
	 * 分页数据
	 */
	private List<?> content;
}
