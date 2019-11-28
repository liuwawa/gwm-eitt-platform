package com.gwm.one.model.hr.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 分组组织中间表
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_group_grouping")
@Builder
public class SysGroupGrouping extends Model<SysGroupGrouping> {

    /**
     * 组织id
     */
    @TableField("groupId")
    private Integer groupId;

    /**
     * 分组id
     */
    @TableField("groupingId")
    private Integer groupingId;


}
