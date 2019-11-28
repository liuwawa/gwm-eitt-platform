package com.gwm.one.hr.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${log-center}")
public interface SmsClient {

	@GetMapping(value = "/notification-anon/internal/phone", params = { "key", "code" })
	String matcheCodeAndGetPhone(@RequestParam("key") String key, @RequestParam("code") String code,
			@RequestParam(value = "delete", required = false) Boolean delete,
			@RequestParam(value = "second", required = false) Integer second);
}
