package com.gwm.one.model.hr.user;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gwm.one.model.hr.user.constants.CredentialType;
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
@TableName(value = "user_credentials")
public class UserCredential implements Serializable {

	private static final long serialVersionUID = -958701617717204974L;
	private String username;
	/**
	 * @see CredentialType
	 */
	private String type;
	private Long userId;

}
