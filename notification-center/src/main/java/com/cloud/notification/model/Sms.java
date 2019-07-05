package com.cloud.notification.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_sms")
public class Sms implements Serializable {

	private static final long serialVersionUID = -1084513813698387690L;

	@TableId(type = IdType.AUTO)
	private Long id;
	private String phone;
	@TableField("signName")
	private String signName;
	@TableField("templateCode")
	private String templateCode;
	private String params;
	@TableField("bizId")
	private String bizId;
	private String code;
	private String message;
	private Date day;
	@TableField("createTime")
	private Date createTime;
	@TableField("updateTime")
	private Date updateTime;
}
