package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cloud.model.user.constants.SysUserResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@ApiModel(value = "SysGroup", description = "组")
public class SysGroup extends Model<SysGroup> {

    /**
     * 组id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 组名称
     */
    @TableField("label")
    @ApiModelProperty(value = "组名称")
    private String label;

    /**
     * 组的父组id(0为没有父级，默认为0)
     */
    @TableField("parentid")
    @ApiModelProperty(value = "组的父级id")
    private Integer parentid;

    /**
     * 定义展示顺序标识
     */
    @TableField("groupShowOrder")
    @ApiModelProperty(value = "定义展示顺序标识")
    private Integer groupShowOrder;

    /**
     * 组的级别(默认为一级组织)
     */
    @TableField("level")
    @ApiModelProperty(value = "组的级别（默认一级组织）")
    private Integer level;

    /**
     * 子节点数(默认0)
     */
    @TableField("groupChildCount")
    @ApiModelProperty(value = "子节点数")
    private Integer groupChildCount;

    /**
     * 备注
     */
    @TableField("groupRemark")
    @ApiModelProperty(value = "备注")
    private String groupRemark;

    /**
     * 组所在区域
     */
    @TableField("groupAddress")
    @ApiModelProperty(value = "组所在区域")
    private String groupAddress;

    /**
     * 组的联系电话
     */
    @TableField("groupTel")
    @ApiModelProperty(value = "组的联系电话")
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
     * 删除标识（0代表存在，1代表已删除）
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
     * 当前操作人
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "当前操作人")
    private String loginAdminName;

    /**
     * 子节点
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子节点")
    private List<SysGroup> children;


    /**
     * 当前组织直接领导工号
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "当前组织直接领导工号")
    private String gDirectLeader;

    /**
     * 部门最高领导工号
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "部门最高领导工号")
    private String gDeptopLeader;

    /**
     * 单位最高领导工号
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "单位最高领导工号")
    private String gUnittopLeader;

    /**
     * 模块
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "模块")
    private String gModule;


    /**
     * 子模块
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "子模块")
    private String subModule;

    /**
     * 当前组织直接领导信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "当前组织直接领导信息")
    private SysUserResponse gDirectLeaderInfo;

    /**
     * 部门最高领导信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "部门最高领导信息")
    private SysUserResponse gDeptopLeaderInfo;

    /**
     * 单位最高领导信息
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "单位最高领导信息")
    private SysUserResponse gUnittopLeaderInfo;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
