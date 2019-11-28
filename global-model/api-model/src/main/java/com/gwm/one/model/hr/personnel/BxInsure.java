package com.gwm.one.model.hr.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 保险类型表
 * </p>
 *
 * @author liuek
 * @since 2019-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("bx_insure")
public class BxInsure extends Model<BxInsure> {

    /**
     * 保险类型id(主键)
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 保险名称
     */
    @TableField("C_Name")
    private String cName;

    /**
     * 公司比例
     */
    @TableField("C_Rate")
    private BigDecimal cRate;

    /**
     * 个人比例
     */
    @TableField("P_Rate")
    private BigDecimal pRate;

    /**
     * 保险基数
     */
    @TableField("Radix")
    private BigDecimal radix;

    /**
     * 开始时间
     */
    @TableField("B_StartDate")
    private LocalDateTime bStartdate;

    /**
     * 结束时间
     */
    @TableField("B_EndDate")
    private LocalDateTime bEnddate;

    /**
     * 保险所属区域
     */
    @TableField("B_Limit")
    private String bLimit;

    /**
     * 职务
     */
    @TableField("P_Duty")
    private String pDuty;

    /**
     * 录入时间
     */
    @TableField("P_InputDate")
    private LocalDateTime pInputdate;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
