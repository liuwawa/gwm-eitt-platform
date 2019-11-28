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
 * 讲师表
 * </p>
 *
 * @author liuek
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("px_innerTeacher")
public class PxInnerTeacher extends Model<PxInnerTeacher> {

    /**
     * 讲师id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private Integer pPersonnelid;

    /**
     * 讲师级别
     */
    @TableField("TeachLevel")
    private String teachLevel;

    @TableField("CDate")
    private LocalDateTime cDate;

    /**
     * 手机号
     */
    @TableField("Tel")
    private String tel;

    /**
     * 讲师特点
     */
    @TableField("TeachSpecial")
    private String teachSpecial;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 认证
     */
    @TableField("attestation")
    private String attestation;

    @TableField("JingPin")
    private String jingPin;

    @TableField("Moudel")
    private Integer moudel;

    /**
     * 认证课程
     */
    @TableField("goal")
    private String goal;

    /**
     * 授课对象
     */
    @TableField("obj")
    private String obj;

    /**
     * 《课程设计与开发训练》培训记录
     */
    @TableField("pxjlkcsj")
    private Integer pxjlkcsj;

    /**
     * 《TTT》培训记录
     */
    @TableField("pxjlttt")
    private Integer pxjlttt;

    /**
     * 0申请1审核通过(认证有效)2认证拒绝3过期无效
     */
    @TableField("state")
    private Integer state;

    /**
     * 审核人
     */
    @TableField("reviewer")
    private String reviewer;

    /**
     * 审核时间
     */
    @TableField("reviewDate")
    private LocalDateTime reviewDate;

    /**
     * 审核时间
     */
    @TableField("reviewIdea")
    private String reviewIdea;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;


    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }

}
