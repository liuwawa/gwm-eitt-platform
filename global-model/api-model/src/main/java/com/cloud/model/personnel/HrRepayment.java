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
 * 长城家园还款记录表
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
@TableName("hr_repayment")
public class HrRepayment extends Model<HrRepayment> {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private Integer pPersonnelid;

    /**
     * 开始时间
     */
    @TableField("R_StartDate")
    private LocalDateTime rStartdate;

    /**
     * 结束时间
     */
    @TableField("R_EndDate")
    private LocalDateTime rEnddate;

    /**
     * 付款金额
     */
    @TableField("R_Money")
    private String rMoney;

    /**
     * 是否已经还款（0未还，1已还）
     */
    @TableField("R_IsPay")
    private Integer rIspay;

    /**
     * 录入时间
     */
    @TableField("C_InputDate")
    private LocalDateTime cInputdate;

    /**
     * 付款时间
     */
    @TableField("R_PayDate")
    private LocalDateTime rPaydate;

    /**
     * 利息
     */
    @TableField("R_Interest")
    private String rInterest;

    /**
     * 方式
     */
    @TableField("R_Type")
    private Integer rType;

    /**
     * 银行卡号
     */
    @TableField("R_Bankcard")
    private String rBankcard;

    /**
    * 偿还金额
    * */
    @TableField(exist = false)
    private String totalMoney;

    public String getTotalMoney() {
        this.totalMoney = Double.parseDouble(this.rInterest) + Double.parseDouble(this.rMoney) + "";
        return totalMoney;
    }

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }





}
