package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户，分组中间表
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@Data
@TableName("sys_user_grouping")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUserGrouping extends Model<SysUserGrouping> {


    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 分组id
     */
    @TableField("groupingId")
    private Integer groupingId;


}
