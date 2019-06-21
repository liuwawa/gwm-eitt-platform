package com.cloud.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class SysGrouping implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Boolean groupingNumber;

    /**
     * 分组创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 分组创建人
     */
    @TableField("createBy")
    private String createBy;

    /**
     * 分组修改时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 分组修改人
     */
    @TableField("updateBy")
    private String updateBy;

    /**
     * 分组删除时间
     */
    @TableField("deleteTime")
    private LocalDateTime deleteTime;

    /**
     * 分组展示顺序
     */
    @TableField("groupingShowOrder")
    private Integer groupingShowOrder;



}
