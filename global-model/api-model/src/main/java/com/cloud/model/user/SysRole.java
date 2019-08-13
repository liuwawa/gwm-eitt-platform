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
 * 角色
 */
@Data
@TableName(value = "sys_role")
@ApiModel(value = "SysRole", description = "角色")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -2054359538140713354L;
    @TableId(type = IdType.AUTO)
   @ApiModelProperty(value = "角色id")
    private Long id;
    @ApiModelProperty(value = "角色code")
    private String code;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @TableField("createBy")
    private String createBy;
    @TableField("createTime")
    private Date createTime;
    @TableField("updateTime")
    private Date updateTime;
    @TableField("roleType")
    @ApiModelProperty(value = "角色类型(1.超级管理员 2.普通管理员 3.普通用户)")
    private String roleType;

    // 勾选标识
    @TableField(exist = false)
    private boolean checked = false;
}
