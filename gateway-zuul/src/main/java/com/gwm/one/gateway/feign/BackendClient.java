package com.gwm.one.gateway.feign;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("${manage-backend}")
public interface BackendClient {

	/**
	 * 内部可以匿名访问
	 * @param params
	 * @return
	 */
	@GetMapping("/backend-anon/internal/blackIPs")
	Set<String> findAllBlackIPs(@RequestParam("params") Map<String, Object> params);
}
