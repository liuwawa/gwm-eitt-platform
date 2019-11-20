package com.gwm.one.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "响应返回值PageResult", description = "分页返回数据")
public class PageResult {
	/**
	 * 当前页码
	 */
	@JsonProperty
	@ApiModelProperty(value = "页码")
	private long pageNum;
	/**
	 * 每页数量
	 */
	@JsonProperty
	@ApiModelProperty(value = "每页条数")
	private long pageSize;
	/**
	 * 记录总数
	 */
	@JsonProperty
	@ApiModelProperty(value = "总条数")
	private long totalSize;
	/**
	 * 页码总数
	 */
	@JsonProperty
	@ApiModelProperty(value = "总页数")
	private long totalPages;
	/**
	 * 分页数据
	 */
	@JsonProperty
	@ApiModelProperty(value = "返回数据")
	private List<?> content;

	@JsonIgnore
	public long getPageNum() {
		return pageNum;
	}

	@JsonIgnore
	public void setPageNum(long pageNum) {
		this.pageNum = pageNum;
	}

	@JsonIgnore
	public long getPageSize() {
		return pageSize;
	}

	@JsonIgnore
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	@JsonIgnore
	public long getTotalSize() {
		return totalSize;
	}

	@JsonIgnore
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	@JsonIgnore
	public long getTotalPages() {
		return totalPages;
	}

	@JsonIgnore
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	@JsonIgnore
	public List<?> getContent() {
		return content;
	}

	@JsonIgnore
	public void setContent(List<?> content) {
		this.content = content;
	}
}
