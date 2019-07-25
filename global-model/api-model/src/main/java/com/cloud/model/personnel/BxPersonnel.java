package com.cloud.model.personnel;

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
 * 个人保险信息表
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
@TableName("bx_personnel")
public class BxPersonnel extends Model<BxPersonnel> {

    /**
     * 个人保险信息Id（主键）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private String pPersonnelid;

    /**
     * 保险编号
     */
    @TableField("Accounts")
    private String Accounts;

    /**
     * 是否停缴(0否,1是)
     */
    @TableField("IsStop")
    private Integer IsStop;

    /**
     * 参保类型
     */
    @TableField("InsuranceType")
    private Integer InsuranceType;

    /**
     * 参保日期
     */
    @TableField("InsuranceDate")
    private LocalDateTime InsuranceDate;

    /**
     * 备注
     */
    @TableField("B_remark")
    private String bRemark;

    /**
     * 对应保险类型信息
     */
    @TableField("insureID")
    private Integer insureID;

    /**
     * 医疗基数
     */
    @TableField("MedicalTreatmentRadix")
    private BigDecimal MedicalTreatmentRadix;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
