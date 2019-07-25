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
 * 长城家园个人信息表
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
@TableName("hr_gwHomeLand")
public class HrGwHomeLand extends Model<HrGwHomeLand> {

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
     * 长城家园房号
     */
    @TableField("C_RoomNumber")
    private String cRoomnumber;

    /**
     * 所在单位
     */
    @TableField("C_Unit")
    private String cUnit;

    /**
     * 所在部门
     */
    @TableField("C_Department")
    private String cDepartment;

    /**
     * 员工姓名
     */
    @TableField("C_PersonName")
    private String cPersonname;

    /**
     * 联系方式
     */
    @TableField("C_Tel")
    private String cTel;

    /**
     * 工资卡号
     */
    @TableField("C_WageCard")
    private String cWagecard;

    /**
     * 借款金额
     */
    @TableField("C_BorrowMoney")
    private String cBorrowmoney;

    /**
     * 借款日期
     */
    @TableField("C_BorrowDate")
    private LocalDateTime cBorrowdate;

    /**
     * 所在工会
     */
    @TableField("C_TradeUnion")
    private String cTradeunion;

    /**
     * 录入日期
     */
    @TableField("C_InputDate")
    private LocalDateTime cInputdate;

    /**
     * 删除标识(0存在 1删除)
     */
    @TableField("isDel")
    private Integer isDel;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
