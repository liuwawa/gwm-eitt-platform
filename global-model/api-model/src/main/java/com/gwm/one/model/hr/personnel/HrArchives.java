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
 * 档案信息表
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
@TableName("hr_archives")
public class HrArchives extends Model<HrArchives> {

    /**
     * 档案id（主键）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    @TableField("P_PersonnelID")
    private String pPersonnelid;

    /**
     * 组织编号
     */
    @TableField("P_GroupID")
    private Integer pGroupid;

    /**
     * 档案编号
     */
    @TableField("A_ArchivesNo")
    private String aArchivesno;

    /**
     * 转入日期
     */
    @TableField("A_Date")
    private LocalDateTime aDate;

    /**
     * 档案类型
     */
    @TableField("A_Class")
    private Integer aClass;

    /**
     * 存放位置
     */
    @TableField("A_Address")
    private String aAddress;

    /**
     * 操作人
     */
    @TableField("A_CName")
    private String aCname;

    /**
     * 转入类型
     */
    @TableField("A_Type")
    private String aType;

    /**
     * 存放单位
     */
    @TableField("A_Unit")
    private String aUnit;

    /**
     * 操作人
     */
    @TableField("A_Hander")
    private String aHander;

    /**
     * 转入原因
     */
    @TableField("A_Reason")
    private String aReason;

    /**
     * 备注
     */
    @TableField("A_Remark")
    private String aRemark;

    /**
     * 是否为党员(0不是，1是)
     */
    @TableField("A_Party")
    private Integer aParty;

    /**
     * 指定主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
