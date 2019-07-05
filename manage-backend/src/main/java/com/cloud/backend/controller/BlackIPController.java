package com.cloud.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.backend.model.BlackIP;
import com.cloud.backend.service.BlackIPService;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class BlackIPController {

    @Autowired
    private BlackIPService blackIPService;

    /**
     * 添加黑名单ip
     */
    @LogAnnotation(module = LogModule.ADD_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:save')")
    @PostMapping("/blackIPs")
    public ResultVo save(@RequestBody BlackIP blackIP) {
        try {
            blackIP.setCreateTime(new Date());
            BlackIP blackIP1 = blackIPService.getOne(new QueryWrapper<BlackIP>().lambda()
                    .eq(BlackIP::getIp, blackIP.getIp()));
            if (blackIP1 != null) {
                return new ResultVo(500,"已经存在ip:"+blackIP.getIp(), null);
            }
            blackIPService.saveBlackIp(blackIP);
            log.info("添加,ip:{}", blackIP.getIp());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("除删出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }

    }

    /**
     * 删除黑名单ip
     */
    @LogAnnotation(module = LogModule.DELETE_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:delete')")
    @DeleteMapping("/blackIPs")
    public ResultVo delete(@RequestParam Integer id) {
        try {
            blackIPService.delete(id);
            log.info("删除成功,id:{}", id);
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
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

    /**
     * 一键删除所有黑名单ip
     *
     * @return
     */
    @LogAnnotation(module = LogModule.DELETE_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:deleteall')")
    @GetMapping("/deleteAll")
    public ResultVo deleteAllBlackIp() {
        try {
            blackIPService.deleteAll();
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("全部删除黑名单ip出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
    }
}
