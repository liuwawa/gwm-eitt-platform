package com.gwm.one.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gwm.one.model.log.Log;

@FeignClient("${log-center}")
public interface LogClient {

	@PostMapping("/logs-anon/internal")
	void saveLog(@RequestBody Log log);
}
