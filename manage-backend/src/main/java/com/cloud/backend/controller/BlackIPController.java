package com.cloud.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.backend.model.BlackIP;
import com.cloud.backend.service.BlackIPService;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class BlackIPController {

	@Autowired
	private BlackIPService blackIPService;

	/**
	 * 添加黑名单ip
	 * 
	 */
	@LogAnnotation(module = LogModule.ADD_BLACK_IP)
	@PreAuthorize("hasAuthority('ip:black:save')")
	@PostMapping("/blackIPs")
	public void save(@RequestBody BlackIP blackIP) {
		blackIP.setCreateTime(new Date());
		blackIPService.save(blackIP);
	}

	/**
	 * 删除黑名单ip
	 * 
	 */
	@LogAnnotation(module = LogModule.DELETE_BLACK_IP)
	@PreAuthorize("hasAuthority('ip:black:delete')")
	@DeleteMapping("/blackIPs")
	public void delete(String ip) {
		blackIPService.delete(ip);
	}

	/**
	 * 查询黑名单
	 * 
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('ip:black:query')")
	@PostMapping("/findPage")
	public PageResult findBlackIPs(@RequestBody Map<String, Object> params) {
		Long pageIndex = Long.valueOf(params.get("pageNum").toString());
		Long pageSize = Long.valueOf(params.get("pageSize").toString());
		IPage<BlackIP> blackIPage = blackIPService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize));
		return PageResult.builder().content(blackIPage.getRecords()).
				pageNum(blackIPage.getCurrent()).
				pageSize(blackIPage.getSize()).
				totalPages(blackIPage.getPages()).
				totalSize(blackIPage.getTotal()).build();
	}

	/**
	 * 查询黑名单<br>
	 * 可内网匿名访问
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/backend-anon/internal/blackIPs")
	public Set<String> findAllBlackIPs(@RequestParam Map<String, Object> params) {
		Page<BlackIP> page = blackIPService.findBlackIPs(params);
		if (page.getTotal() > 0) {
			return page.getData().stream().map(BlackIP::getIp).collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}
}
