package com.cloud.model.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账号类型
 * 
 * @author lz
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_credential")
public class UserCredential implements Serializable {

	private static final long serialVersionUID = -958701617717204974L;

	private String username;
	/**
	 * @see com.cloud.model.user.constants.CredentialType
	 */
	private String type;
	private Long userId;

}
