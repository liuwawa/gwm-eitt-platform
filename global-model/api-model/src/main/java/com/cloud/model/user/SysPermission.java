package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 权限标识
 * 
 * @author lz
 *
 */
@Data
@TableName(value = "sys_permission")
public class SysPermission implements Serializable {

	private static final long serialVersionUID = 280565233032255804L;
	@TableId(type = IdType.AUTO)
	private Long id;
	private String permission;
	private String name;
//	@TableField("createTime")
	private Date createTime;
//	@TableField("updateTime")
	private Date updateTime;
	@TableField(exist = false)
	private boolean checked = false;

}
