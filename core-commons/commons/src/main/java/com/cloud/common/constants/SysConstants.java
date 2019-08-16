package com.cloud.common.constants;

/**
 * 常量管理
 * @author wp
 * @date 2019.7.3
 */ 
public interface SysConstants {

	/**
	 * 系统管理员角色id
	 */
	Long ADMIN_ROLE_ID = 1L;

	/**
	 * 超级管理员用户id
	 */
	Long ADMIN_USER_ID = 2L;

	/**
	 * 超级管理员Code
	 */
	String ADMIN_CODE = "SUPER_ADMIN";

	/**
	 * 角色类型 1 超级管理员 2 普通管理员  3 普通用户
	 */
	String SUPER_ADMIN_ROLE_TYPE = "1";
	String COMMON_ADMIN_ROLE_TYPE = "2";
	String COMMON_USER_ROLE_TYPE = "3";

	public static final String PAST = "PAST|";


	
}
