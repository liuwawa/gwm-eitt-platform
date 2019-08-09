package com.cloud.model.user;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色
 */
@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -2054359538140713354L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String name;
    @TableField("createBy")
    private String createBy;
    @TableField("createTime")
    private Date createTime;
    @TableField("updateTime")
    private Date updateTime;
    @TableField("roleType")
    private String roleType;

    // 勾选标识
    @TableField(exist = false)
    private boolean checked = false;
}
