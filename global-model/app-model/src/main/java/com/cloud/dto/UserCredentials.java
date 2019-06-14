package com.cloud.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户凭证表
 * </p>
 *
 * @author maxIn
 * @since 2019-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserCredentials implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名或手机号等
     */
    private String username;

    /**
     * 账号类型（用户名、手机号）
     */
    private String type;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;


}
