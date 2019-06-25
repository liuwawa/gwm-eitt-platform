package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@TableName("sys_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysGroup extends Model<SysGroup> {

    /**
     * 组id
     */
    @TableId(value = "groupId", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 组名称
     */
    @TableField("groupName")
    private String groupName;

    /**
     * 组的父组id(0为没有父级，默认为0)
     */
    @TableField("groupParentId")
    private Integer groupParentId;

    /**
     * 定义展示顺序标识
     */
    @TableField("groupShowOrder")
    private Integer groupShowOrder;

    /**
     * 组的级别(默认为一级组织)
     */
    @TableField("groupLevel")
    private Integer groupLevel;

    /**
     * 子节点数(默认0)
     */
    @TableField("groupChildCount")
    private Integer groupChildCount;

    /**
     * 备注
     */
    @TableField("groupRemark")
    private String groupRemark;

    /**
     * 组所在区域
     */
    @TableField("groupAddress")
    private String groupAddress;

    /**
     * 组的联系电话
     */
    @TableField("groupTel")
    private String groupTel;

    /**
     * 启用时间
     */
    @TableField("enableTime")
    private Date enableTime;

    /**
     * 是否更新
     */
    @TableField("isUpdate")
    private String isUpdate;

    /**
     * 删除标识（0代表存在，2代表已删除）
     */
    @TableField("isDel")
    private String isDel;

    /**
     * 删除人
     */
    @TableField("deleteBy")
    private String deleteBy;

    /**
     * 删除时间
     */
    @TableField("deleteTime")
    private Date deleteTime;

    /**
     * 创建人
     */
    @TableField("createBy")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField("updateBy")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField("updateTime")
    private Date updateTime;


    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return groupId;
    }
}
