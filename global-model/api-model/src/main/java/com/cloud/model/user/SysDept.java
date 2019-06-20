package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
* 王懋云
* 2019.6.19
* */
@Data
public class SysDept implements Serializable {

    private static final long serialVersionUID = 64325354314321432L;

    //部门id
    private Long deptId;

    //父部门
    private Long parentId;

    //祖级列表
    private String ancestors;

    //部门名称
    private String deptName;

    //负责人
    private String leader;

    //联系电话
    private String phone;

    //电子邮件
    private String email;


    private char status;
    private char delFlag;
    private String createBy;
    private String updateBy;
    private Date createTime;
    private Date updateTime;





}
