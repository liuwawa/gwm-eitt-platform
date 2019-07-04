package com.cloud.backend.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * IP黑名单
 * 
 * @author lz
 *
 */
@Data
@TableName(value = "black_ip")
public class BlackIP implements Serializable {

	private static final long serialVersionUID = -2064187103535072261L;
	@TableId(type = IdType.AUTO)
	private String ip;
	@TableField("createTime")
	private Date createTime;
}
