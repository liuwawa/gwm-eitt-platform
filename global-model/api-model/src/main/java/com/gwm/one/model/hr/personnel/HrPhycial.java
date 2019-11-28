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
 * 体检信息表
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
@TableName("hr_phycial")
public class HrPhycial extends Model<HrPhycial> {

    /**
     * 体检信息id（主键）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private String pPersonnelid;

    /**
     * 体检时间
     */
    @TableField("PL_Date")
    private LocalDateTime plDate;

    /**
     * 身高
     */
    @TableField("PL_Height")
    private String plHeight;

    /**
     * 体重
     */
    @TableField("PL_Weight")
    private String plWeight;

    /**
     * BMI指数
     */
    @TableField("PL_BMI")
    private String plBmi;

    /**
     * 是否吸烟(0否，1是)
     */
    @TableField("PL_Smoking")
    private Integer plSmoking;

    /**
     * 左眼视力
     */
    @TableField("PL_Leftvision")
    private String plLeftvision;

    /**
     * 右眼视力
     */
    @TableField("PL_Rightvision")
    private String plRightvision;

    /**
     * 血压
     */
    @TableField("PL_BloodPressure")
    private String plBloodpressure;

    /**
     * 血压值
     */
    @TableField("PL_BloodPressurevalue")
    private String plBloodpressurevalue;

    /**
     * 内科
     */
    @TableField("PL_Internist")
    private String plInternist;

    /**
     * 胸透
     */
    @TableField("PL_ChestXrays")
    private String plChestxrays;

    /**
     * 彩超
     */
    @TableField("PL_Colorultrasonic")
    private String plColorultrasonic;

    /**
     * 乙肝五项
     */
    @TableField("PL_HepatitisB")
    private String plHepatitisb;

    /**
     * 抗体情况
     */
    @TableField("PL_Antibody")
    private String plAntibody;

    /**
     * 肝功情况
     */
    @TableField("PL_Liverfunction")
    private String plLiverfunction;

    /**
     * 肾功情况
     */
    @TableField("PL_Renalfunction")
    private String plRenalfunction;

    /**
     * 血糖
     */
    @TableField("PL_BloodSugar")
    private String plBloodsugar;

    /**
     * 血脂
     */
    @TableField("PL_BloodFat")
    private String plBloodfat;

    /**
     * 心电图
     */
    @TableField("PL_Electrocardiogram")
    private String plElectrocardiogram;

    /**
     * 其他
     */
    @TableField("PL_Other")
    private String plOther;

    /**
     * 体检结果
     */
    @TableField("PL_Result")
    private String plResult;

    /**
     * 综合结论
     */
    @TableField("PL_Conclusion")
    private String plConclusion;

    /**
     * 医生意见
     */
    @TableField("PL_DoctorAdvice")
    private String plDoctoradvice;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
