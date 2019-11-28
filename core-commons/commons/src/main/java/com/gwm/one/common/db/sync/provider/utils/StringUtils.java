package com.gwm.one.common.db.sync.provider.utils;

/**
 * 字符串工具类
 * @author wangpan
 *
 */
public class StringUtils {
	
	public static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}
