package com.gwm.one.model.hr.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 长城家园利率表
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
@TableName("hr_interest")
public class HrInterest extends Model<HrInterest> {

    /**
     * 主键（利率编号）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 利率日期
     */
    @TableField("L_Date")
    private LocalDateTime lDate;

    /**
     * 利率年限
     */
    @TableField("L_InterestRateLimit")
    private Integer lInterestratelimit;

    /**
     * 利率
     */
    @TableField("L_Interest")
    private String lInterest;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
