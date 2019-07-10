package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 组织分组表
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@TableName("sys_grouping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysGrouping extends Model<SysGrouping> {

    /**
     * 分组表id主键
     */
    @TableId("groupingId")
    private Integer groupingId;

    /**
     * 分组名称
     */
    @TableField("groupingName")
    private String groupingName;

    /**
     * 分组备注
     */
    @TableField("groupingRemark")
    private String groupingRemark;

    /**
     * 删除标识(0存在，1删除)
     */
    @TableField("isDel")
    private String isDel;

    /**
     * 该分组共组织数
     */
    @TableField("groupingNumber")
    private Integer groupingNumber;

    /**
     * 分组创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 分组创建人
     */
    @TableField("createBy")
    private String createBy;

    /**
     * 分组修改时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 分组修改人
     */
    @TableField("updateBy")
    private String updateBy;

    /**
     * 分组删除时间
     */
    @TableField("deleteTime")
    private Date deleteTime;

    /**
     * 分组删除人
     */
    @TableField("deleteBy")
    private String deleteBy;
    /**
     * 分组展示顺序
     */
    @TableField("groupingShowOrder")
    private Integer groupingShowOrder;
    /**
     * 当前操作人
     */
    @TableField(exist = false)
    private String loginAdminName;

    /**
     * 分组之下的组织
     */
    @TableField(exist = false)
    private List<SysGroup> children;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return groupingId;
    }

}
