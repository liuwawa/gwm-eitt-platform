package com.cloud.model.personnel;

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
 * 合同信息表
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
@TableName("hr_contract")
public class HrContract extends Model<HrContract> {

    /**
     * 合同id（主键）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private String pPersonnelid;

    /**
     * 所在科室
     */
    @TableField("C_GroupID")
    private Integer cGroupid;

    /**
     * 开始日期
     */
    @TableField("C_StartDate")
    private LocalDateTime cStartdate;

    /**
     * 结束日期
     */
    @TableField("C_EndDate")
    private LocalDateTime cEnddate;

    /**
     * 合同类型
     */
    @TableField("C_Type")
    private Integer cType;

    /**
     * 合同编号
     */
    @TableField("C_ContractNO")
    private String cContractno;

    /**
     * 合同状态
     */
    @TableField("C_Status")
    private String cStatus;

    /**
     * 是否有试用期（0否，1是）
     */
    @TableField("C_HasTry")
    private Boolean cHastry;

    /**
     * 赔偿金
     */
    @TableField("C_BreachMoney")
    private String cBreachmoney;

    /**
     * 录入日期
     */
    @TableField("C_InputDate")
    private LocalDateTime cInputdate;

    /**
     * 操作人
     */
    @TableField("C_Directory")
    private String cDirectory;

    /**
     * 备注
     */
    @TableField("C_Remark")
    private String cRemark;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }

}
