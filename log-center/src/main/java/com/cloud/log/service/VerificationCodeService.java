package com.cloud.log.service;


import com.cloud.model.log.VerificationCode;

public interface VerificationCodeService {

	VerificationCode generateCode(String phone);

	String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);
}
