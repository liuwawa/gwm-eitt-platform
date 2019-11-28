package com.gwm.one.model.hr.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 组织拓展表
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@TableName("sys_group_expand")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysGroupExpand extends Model<SysGroupExpand> {


    /**
     * 组织表拓展表id
     */
    @TableId("gExpandId")
    private Integer gExpandId;

    /**
     * 单位id
     */
    @TableField("unitId")
    private Integer unitId;

    /**
     * 单位名称
     */
    @TableField("unitName")
    private String unitName;

    /**
     * 部门id
     */
    @TableField("deptId")
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField("deptName")
    private String deptName;

    /**
     * 科室id
     */
    @TableField("teamId")
    private Integer teamId;

    /**
     * 科室名字
     */
    @TableField("teamName")
    private String teamName;

    /**
     * 模块
     */
    @TableField("gModule")
    private String gModule;

    /**
     * 子模块
     */
    @TableField("subModule")
    private String subModule;
    /**
     * 该组织全称（路径）
     */
    @TableField("gFullname")
    private String gFullname;

    /**
     * 组织级别（0集团级别 10公司级 20部门级 30科室级）
     */
    @TableField("gGrade")
    private String gGrade;

    /**
     * 当前组织直接领导工号
     */
    @TableField("gDirectLeader")
    private String gDirectLeader;

    /**
     * 部门最高领导工号
     */
    @TableField("gDeptopLeader")
    private String gDeptopLeader;

    /**
     * 单位最高领导工号
     */
    @TableField("gUnittopLeader")
    private String gUnittopLeader;

    /**
     * 关联group主表的标识
     */
    @TableField("groupId")
    private Integer groupId;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return gExpandId;
    }
}
