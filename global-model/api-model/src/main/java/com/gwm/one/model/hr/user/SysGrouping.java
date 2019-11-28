package com.gwm.one.model.hr.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@EqualsAndHashCode
@ApiModel(value = "SysGrouping", description = "组织分组")
public class SysGrouping extends Model<SysGrouping> {

    /**
     * 分组表id主键
     */
    @TableId("groupingId")
    @ApiModelProperty(value = "分组id")
    private Integer groupingId;

    /**
     * 分组名称
     */
    @TableField("groupingName")
    @ApiModelProperty(value = "分组名称")
    private String groupingName;

    /**
     * 分组备注
     */
    @TableField("groupingRemark")
    @ApiModelProperty(value = "分组备注")
    private String groupingRemark;

    /**
     * 删除标识(0存在，1删除)
     */
    @TableField("isDel")
    private String isDel;

    /**
     * 该分组共组织数
     */
    @TableField("groupNumber")
    @ApiModelProperty(value = "该分组共组织数")
    private Integer groupNumber;

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
    @ApiModelProperty(value = "分组展示顺序")
    private Integer groupingShowOrder;
    /**
     * 当前操作人
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "当前操作人")
    private String loginAdminName;

    /**
     * 分组之下的组织
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "分组之下的组织")
    private List<SysGroup> children;

    /**
     * 用户是否可以操作该分组的标识
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "分组检查可用性")
    private Boolean checked;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return groupingId;
    }

}
