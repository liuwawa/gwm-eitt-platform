package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 611197991672067628L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private Integer sex;
    private Integer departmentId;
    /**
     * 状态
     */
    private Boolean enabled;
    private String type;
    private Date createTime;
    private Date updateTime;

}
