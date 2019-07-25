package com.cloud.log.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.common.Page;
import com.cloud.model.log.Sms;

import java.util.Map;

public interface SmsService extends IService<Sms> {

	void save(Sms sms, Map<String, String> params);

	void update(Sms sms);

	/**
	 * @param id
	 * @return
	 */
//	Sms findById(Long id);

	Page<Sms> findSms(Map<String, Object> params);

	/**
	 * 发送短信
	 */
	SendSmsResponse sendSmsMsg(Sms sms);

	void delAllSms();
}
