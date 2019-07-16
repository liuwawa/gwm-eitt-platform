package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_user")//表名
public class SysUser implements Serializable {

    private static final long serialVersionUID = 611197991672067628L;
    @TableId(type = IdType.AUTO)//主键
    private Long id;
    private String username;
    private String password;
    private String nickname;
    @TableField("headImgUrl")//指定数据库的字段名
    private String headImgUrl;
    private String phone;
    private Integer sex;

    /**
     * 状态
     */
    private Boolean enabled;
    private String type;
    @TableField("createTime")
    private Date createTime;
    @TableField("updateTime")
    private Date updateTime;
    /**
     * 所在组织id
     */
    @TableField("groupId")
    private Integer groupId;
    /**
     * 传前台的数据
     */
    @TableField(exist = false)
    private SysGroup group;
    /**
     * 工号
     */
    @TableField("personnelID")
    private String personnelID;
    /**
     * 职务
     */
    @TableField("duties")
    private String duties;
}
