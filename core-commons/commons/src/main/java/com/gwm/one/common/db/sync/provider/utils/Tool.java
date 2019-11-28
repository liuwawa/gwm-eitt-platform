package com.gwm.one.common.db.sync.provider.utils;

/**
 * 工具类
 * 
 * @author wangpan
 *
 */
public class Tool {
	public static String generateString(int length) {
		if (length < 1) length = 6;
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String genStr = "";
		for (int index = 0; index < length; index++) {
			genStr = genStr + str.charAt((int) ((Math.random() * 100) % 26));
		}
		return genStr;
	}
}
