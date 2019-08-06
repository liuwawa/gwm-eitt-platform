package com.cloud.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具
 */
@Slf4j
public class PhoneUtil {

	private static String REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[6])|(17[013678])|(18[0,5-9])|(19[8,9]))\\d{8}$";
	private static Pattern P = Pattern.compile(REGEX);

	/**
	 * 校验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (phone == null || phone.length() != 11) {
			return Boolean.FALSE;
		}

		Matcher m = P.matcher(phone);
		return m.matches();
	}

	/**
	 * 发送短信
	 * @param phone 手机号
	 * @param content 短信内容
	 */
	public static void sendCode(String phone, String content) {
		if (!PhoneUtil.checkPhone(phone)) {
			throw new IllegalArgumentException("手机号格式不正确");
		}
		//生成验证码
		String account = "344932"; // 用户名（必填）
		String password = "FuG7HB8b"; // 密码（必填）
		String sign = "【长城汽车】"; // 短信签名（必填）
		String subcode = ""; // 子号码（选填）
		String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
		String sendtime = ""; // 定时发送时间（可选）
		try {
			JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
			jsonHttpClient.setRetryCount(1);
			// 调用sendSms方法
			String sendhRes = jsonHttpClient.sendSms(account, password, phone, content, sign, subcode);
			log.info("提交单条普通短信响应：" + sendhRes);
		} catch (Exception e) {
			log.error("短信服务异常", e);
		}
	}
}
