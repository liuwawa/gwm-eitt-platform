package com.gwm.one.common.enums;

import lombok.Getter;

/**
 * 后台返回结果集枚举
 *
 * @author lz
 * @date 2018/8/14
 */
@Getter
public enum ResultEnum {

    /**
     * 账户问题
     */
    USER_EXIST(401, "该用户名已经存在"),
    USER_PWD_NULL(402, "密码不能为空"),
    USER_INEQUALITY(403, "两次密码不一致"),
    USER_OLD_PWD_ERROR(404, "原来密码不正确"),
    USER_NAME_PWD_NULL(405, "用户名和密码不能为空"),
    USER_CAPTCHA_ERROR(406, "验证码错误"),
    USERID_ISNULL(407,"用户id为空"),
    /**
     * 部门问题
     */
    DEPT_EXIST_USER(401, "部门存在用户，无法删除"),

    /**
     * 非法操作
     */
    STATUS_ERROR(401, "非法操作：状态有误"),

    /**
     * 权限问题
     */
    NO_PERMISSIONS(401, "权限不足！"),
    NO_ADMIN_AUTH(500, "不允许操作超级管理员"),
    NO_ADMIN_STATUS(501, "不能修改超级管理员状态"),
    NO_ADMINROLE_AUTH(500, "不允许操作管理员角色"),
    NO_ADMINROLE_STATUS(501, "不能修改管理员角色状态"),

    /**
     * 文件操作
     */
    NO_FILE_NULL(401, "文件不能为空"),
    NO_FILE_TYPE(402, "不支持该文件类型"),

    /*
    * 健康积分操作
    * */
    CREDIT_NULL(401, "健康积分值为空"),


    /*******************************************************/
    /**
     * 分组相关
     */
    GROUPINGNAME_NULL(401,"分组名为空值"),
    GROUPINGID_NULL(402,"分组的id为空值"),

    GROUPING_NOT_EXIST(403,"该分组不存在"),
    /**
     * 组织相关
     */
    GROUPID_NULL(401,"组织id为空值"),
    GROUPNAME_NULL(402,"组织名为空值"),

    GROUP_NOT_EXIST(403,"该组织不存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
