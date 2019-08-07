package com.cloud.model.personnel;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author liuek
 * @since 2019-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("hr_personnel")
public class HrPersonnel extends Model<HrPersonnel> {

    /**
     * 主键编号
     */
    @TableField("id")
    private Integer id;

    /**
     * 工号（自动生成）
     */
    @TableField("P_PersonnelNo")
    private String pPersonnelno;

    /**
     * 曾用名
     */
    @TableField("P_OName")
    private String pOname;

    /**
     * 姓名
     */
    @TableField("P_NName")
    private String pNname;

    /**
     * 性别
     */
    @TableField("P_Sex")
    private Integer pSex;

    /**
     * 婚姻状况
     */
    @TableField("P_Marriage")
    private Integer pMarriage;

    /**
     * 出生日期
     */
    @TableField("P_BirthDate")
    private LocalDateTime pBirthdate;

    /**
     * 身份证号码
     */
    @TableField("P_IDCard")
    private String pIdcard;

    /**
     * 籍贯
     */
    @TableField("P_NativePlace")
    private String pNativeplace;

    /**
     * 户口所在地
     */
    @TableField("P_RegisterResidence")
    private String pRegisterresidence;

    /**
     * 居住类型
     */
    @TableField("P_DwellingType")
    private Integer pDwellingtype;

    /**
     * 现居住地
     */
    @TableField("P_Dwelling")
    private String pDwelling;

    /**
     * 体重
     */
    @TableField("P_Weight")
    private String pWeight;

    /**
     * 身高
     */
    @TableField("P_Height")
    private String pHeight;

    /**
     * 血型
     */
    @TableField("P_BloodType")
    private Integer pBloodtype;

    /**
     * 政治面貌
     */
    @TableField("P_Polity")
    private Integer pPolity;

    /**
     * 民族
     */
    @TableField("P_Native")
    private Integer pNative;

    /**
     * 健康状况
     */
    @TableField("P_Health")
    private String pHealth;

    /**
     * 档案是否在公司
     */
    @TableField("P_ArchivesIsInCorp")
    private String pArchivesisincorp;

    /**
     * 户口是否在公司
     */
    @TableField("P_RResidenceIsInCorp")
    private String pRresidenceisincorp;

    /**
     * 学历
     */
    @TableField("P_Diploma")
    private Integer pDiploma;

    /**
     * 其他证书
     */
    @TableField("P_OtherCertificate")
    private String pOthercertificate;

    /**
     * 专业
     */
    @TableField("P_Professional")
    private String pProfessional;

    /**
     * 毕业学校
     */
    @TableField("P_GraduationSchool")
    private String pGraduationschool;

    /**
     * 毕业日期
     */
    @TableField("P_GraduationDate")
    private LocalDateTime pGraduationdate;

    /**
     * 其他语言
     */
    @TableField("P_OtherLanguage")
    private String pOtherlanguage;

    /**
     * 英语等级
     */
    @TableField("P_EnglishAbility")
    private Integer pEnglishability;

    /**
     * 计算机等级
     */
    @TableField("P_ComputerAbility")
    private Integer pComputerability;

    /**
     * 联系方式
     */
    @TableField("P_Mobile")
    private String pMobile;

    /**
     * 家庭电话
     */
    @TableField("P_HomePhone")
    private String pHomephone;

    /**
     * 邮箱
     */
    @TableField("P_Email")
    private String pEmail;

    /**
     * MSN
     */
    @TableField("P_MSN")
    private String pMsn;

    /**
     * 职称
     */
    @TableField("P_PostTitle")
    private Integer pPosttitle;

    /**
     * 所在科室
     */
    @TableField("P_GroupID")
    private Integer pGroupid;

    /**
     * 工作类型
     */
    @TableField("P_WorkType")
    private Integer pWorktype;

    /**
     * 职务
     */
    @TableField("P_Duty")
    private Integer pDuty;

    /**
     * 职类
     */
    @TableField("P_PositionType")
    private Integer pPositiontype;

    /**
     * 职系
     */
    @TableField("P_PositionSerial")
    private Integer pPositionserial;

    /**
     * 职位
     */
    @TableField("P_Position")
    private Integer pPosition;

    /**
     * 职级
     */
    @TableField("P_PositionLevel")
    private Integer pPositionlevel;

    /**
     * 工龄
     */
    @TableField("P_WorkAge")
    private BigDecimal pWorkage;

    /**
     * 入厂日期
     */
    @TableField("P_JoinDate")
    private LocalDateTime pJoindate;

    /**
     * 人员状态（1在职、2离职）
     */
    @TableField("P_Status")
    private Integer pStatus;

    /**
     * 备注信息
     */
    @TableField("P_Remark")
    private String pRemark;

    /**
     * 录入日期
     */
    @TableField("P_InputDate")
    private LocalDateTime pInputdate;

    /**
     * 是否删除
     */
    @TableField("P_Delete")
    private Integer pDelete;

    /**
     * 人员类型
     */
    @TableField("P_PersonType")
    private Integer pPersontype;

    /**
     * 简历编号
     */
    @TableField("P_ResumeNubmer")
    private String pResumenubmer;

    /**
     * 录入人
     */
    @TableField("P_InputPersonnel")
    private String pInputpersonnel;

    /**
     * 教育类型
     */
    @TableField("P_EduType")
    private Integer pEdutype;

    /**
     * 档案编号
     */
    @TableField("P_ArchiveNo")
    private String pArchiveno;

    @TableField("P_ResidenceNo")
    private String pResidenceno;

    /**
     * 档案所在单位
     */
    @TableField("P_ArchiveUnit")
    private String pArchiveunit;

    @TableField("P_ResidenceUnit")
    private String pResidenceunit;

    /**
     * 是否外籍人
     */
    @TableField("P_Foregin")
    private Integer pForegin;

    /**
     * 是否二代身份证阅读器验证
     */
    @TableField("P_IsCheck")
    private Integer pIscheck;

    @TableField("P_GZState")
    private Integer pGzstate;

    /**
     * 工作所在地
     */
    @TableField("G_Address")
    private Integer gAddress;

    /**
     * 是否更新（0否，1是）
     */
    @TableField("isUpdate")
    private Integer isUpdate;

    /**
     * 直接领导编号
     */
    @TableField("directPersonnleID")
    private Integer directPersonnleID;

    /**
     * 审批权力编号
     */
    @TableField("datePower")
    private Integer datePower;

    @TableField("P_TreatmentLevel")
    private Integer pTreatmentlevel;

    @TableField("P_CostOwner")
    private Integer pCostowner;

    /**
     * 职位编码
     */
    @TableField("P_PositionCode")
    private String pPositioncode;

    /**
     * 岗位实践阶段
     */
    @TableField("P_PositionStep")
    private Integer pPositionstep;

    @TableField("P_BankCard")
    private String pBankcard;

    /**
     * 细分岗位
     */
    @TableField("P_Position_Son")
    private String pPositionSon;

    @TableField("P_OutsideWorkAge")
    private BigDecimal pOutsideworkage;

    /**
     * 职务等级(0员工级 10主管级 20科级 30部级 40副总级 50总经理级 99高层)
     */
    @TableField("P_DutyLevel")
    private Integer pDutylevel;

    /**
     * 证件姓名
     */
    @TableField("P_CardName")
    private String pCardname;

    /**
     * 证件号码
     */
    @TableField("P_CardNo")
    private String pCardno;

    @TableField("P_CardType")
    private Integer pCardtype;

    /**
     * 信息修改时间
     */
    @TableField("P_ModifyDate")
    private LocalDateTime pModifydate;

    /**
     * 启用员工
     */
    @TableField("P_EnableDate")
    private LocalDateTime pEnabledate;

    /**
     * 合同日期
     */
    @TableField(exist = false)
    private String contractDate;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }

}
