package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 权限标识
 * 
 * @author lz
 *
 */
@Data
@TableName(value = "sys_permission")
@ApiModel(value = "SysPermission", description = "权限")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "权限id")
	private Long id;
	@ApiModelProperty(value = "权限标识")
	private String permission;
	@ApiModelProperty(value = "权限名称")
	private String name;

	private Date createTime;

	private Date updateTime;
	@TableField(exist = false)
	private boolean checked = false;

}
