package com.cloud.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.backend.model.BlackIP;
import com.cloud.backend.service.BlackIPService;
import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "黑名单", tags = {"黑名单操作接口 BlackIPController"})
public class BlackIPController {

    @Autowired
    private BlackIPService blackIPService;

    /**
     * 添加黑名单ip
     */
    @LogAnnotation(module = LogModule.ADD_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:save')")
    @PostMapping("/saveBlackIP")
    @ApiOperation(value = "添加黑名单ip")
    public ResultVo saveBlackIP(@ApiParam(value = "BlackIP",required = true) @RequestBody BlackIP blackIP) {
        try {
            blackIP.setCreateTime(new Date());
            BlackIP blackIP1 = blackIPService.getOne(new QueryWrapper<BlackIP>().lambda()
                    .eq(BlackIP::getIp, blackIP.getIp()));
            if (blackIP1 != null) {
                return new ResultVo(500, "已经存在ip:" + blackIP.getIp(), null);
            }
            blackIPService.saveBlackIp(blackIP);
            log.info("添加,ip:{}", blackIP.getIp());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }

    }

    /**
     * 删除黑名单ip
     */
    @LogAnnotation(module = LogModule.DELETE_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:delete')")
    @DeleteMapping("/deleteIp")
    @ApiOperation(value = "删除黑名单ip")
    public ResultVo deleteIp(@ApiParam(value = "id",required = true)@RequestParam Integer id) {
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
    @ApiOperation(value = "分页，多条件查询黑名单",notes = "参数：pageNum（必填），pageSize（必填），ip(对象)")
    public PageResult findBlackIPsByPage(
            @ApiJsonObject(name = "分页，多条件查询黑名单", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "ip", example = "ip", description = "ip")})
            @RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String ipAddress = String.valueOf(params.get("ip").toString());
        IPage<BlackIP> blackIPage = blackIPService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                new QueryWrapper<BlackIP>()
                        .like("ip", ipAddress));
        return PageResult.builder().content(blackIPage.getRecords()).
                pageNum(blackIPage.getCurrent()).
                pageSize(blackIPage.getSize()).
                totalPages(blackIPage.getPages()).
                totalSize(blackIPage.getTotal()).build();
    }



    /**
     * 一键删除所有黑名单ip
     *
     * @return
     */
    @LogAnnotation(module = LogModule.DELETE_BLACK_IP)
    @PreAuthorize("hasAuthority('ip:black:deleteall')")
    @GetMapping("/deleteAll")
    @ApiOperation(value = "一键删除所有黑名单ip")
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
