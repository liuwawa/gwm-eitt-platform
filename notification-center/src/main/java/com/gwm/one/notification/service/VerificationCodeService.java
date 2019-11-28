package com.gwm.one.notification.service;

import com.gwm.one.notification.model.VerificationCode;

public interface VerificationCodeService {

	VerificationCode generateCode(String phone);

	String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);
}
