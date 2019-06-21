package com.cloud.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志对象
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

	private static final long serialVersionUID = -5398795297842978376L;

	private Long id;
	/** 用户名 */
	private String username;
	/**用户id*/
	private Integer userid;
	/** 模块 */
	private String module;
	/**用户ip*/
	private String ip;
	/** 参数值 */
	private String params;
	private String remark;
	/** 是否执行成功 */
	private Boolean flag;
	private Date createTime;
}
