package com.cloud.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.service.SysGroupingService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织分组表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/grouping")
@Slf4j
@Api(value = "组织分组", tags = {"groupingController"})
public class SysGroupingController {
    @Autowired
    private SysGroupingService sysGroupingService;

    /**
     * @param sysGrouping 需要添加的分组
     * @return 操作结果
     * 添加分组
     */
    @LogAnnotation(module = LogModule.ADD_GROUPING)
    @PreAuthorize("hasAuthority('back:group:save')")
    @PostMapping("/saveGrouping")
    public ResultVo saveGrouping(@RequestBody SysGrouping sysGrouping) {
        try {
            if (!sysGroupingService.save(sysGrouping)) {
                log.info("添加分组操作失败，添加的分组名称:{}", sysGrouping.getGroupingName());
                return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
            }
            log.info("添加分组操作成功，添加的分组名称:{}", sysGrouping.getGroupingName());
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加分组，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }

    }

    /**
     * @param groupingId 需要获取详情的id
     * @return 状态和结果数据
     * 根据id查询出该分组的详情
     */
    @GetMapping("/groupingDetail/{groupingId}")
    public ResultVo<SysGrouping> getGroupingDetailById(@PathVariable Integer groupingId) {
        SysGrouping groupingDetail = sysGroupingService.getById(groupingId);
        return new ResultVo<SysGrouping>(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, groupingDetail);
    }

    /**
     * @param sysGrouping 需要修改的数据
     * @return 修改结果
     * 对分组进行修改
     */
    @LogAnnotation(module = LogModule.UPDATE_GROUPING)
    @PreAuthorize("hasAuthority('back:group:update')")
    @PutMapping("/updateGrouping")
    public ResultVo updateGroupingById(@RequestBody SysGrouping sysGrouping) {
        try {
            // 设置修改时间
            sysGrouping.setUpdateTime(new Date());
            sysGrouping.setUpdateBy(sysGrouping.getLoginAdminName());
            if (!sysGroupingService.updateById(sysGrouping)) {
                log.info("编辑分组操作失败，编辑的分组id:{}", sysGrouping.getGroupingId());
                return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
            }
            log.info("编辑分组操作成功，编辑的分组id:{}", sysGrouping.getGroupingId());
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("编辑分组，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }

    }

    /**
     * @param pageIndex 当前页
     * @param pageSize  每页数量
     * @return 分页查询结果
     * 分组的分页查询
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping(value = "/groupingByPage")
    public Page<SysGrouping> selectGroupingByPage(@RequestParam(value = "pageIndex") Integer pageIndex,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        pageSize = pageSize == null ? 15 : pageSize;
        // 分页查isDel为0的数据
        IPage<SysGrouping> groupingIPage =
                sysGroupingService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                        new QueryWrapper<SysGrouping>().lambda()
                                .eq(SysGrouping::getIsDel, 0)
                                .orderByAsc(SysGrouping::getGroupingShowOrder));
        log.info("当前页:{},总页数:{},总个数:{},每页数:{}",
                groupingIPage.getCurrent(), groupingIPage.getPages(), groupingIPage.getTotal(), groupingIPage.getSize());
        return new Page<>((int) groupingIPage.getTotal(), groupingIPage.getRecords());
    }

    /**
     * @return 总个数和结果
     * 查询所有的分组数据
     */
    @GetMapping(value = "/allGrouping")
    public Page<SysGrouping> selectAllGrouping() {
        int count = sysGroupingService.count();
        log.info("总条数:{}", count);
        return new Page<SysGrouping>(count, sysGroupingService.list(new QueryWrapper<SysGrouping>().lambda()
                .eq(SysGrouping::getIsDel, 0)
                .orderByAsc(SysGrouping::getGroupingShowOrder)));
    }

    /**
     * @param sysGrouping 需要删除的分组对象
     * @return 删除结果
     * 单项删除分组(逻辑删除)
     */
    @LogAnnotation(module = LogModule.DELETE_GROUPING)
    @PreAuthorize("hasAuthority('back:group:delete')")
    @PutMapping("/deleteGrouping")
    public ResultVo deleteGrouping(@RequestBody SysGrouping sysGrouping) {
        //设置删除时间，删除人
        sysGrouping.setDeleteTime(new Date());
        sysGrouping.setDeleteBy(sysGrouping.getLoginAdminName());
        sysGrouping.setIsDel("1");
        try {
            if (!sysGroupingService.updateById(sysGrouping)) {
                log.info("删除分组操作失败，删除的分组Id:{}", sysGrouping.getGroupingId());
                return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
            }
            log.info("删除分组操作成功，删除的分组Id:{}", sysGrouping.getGroupingId());
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(单删)，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }
    }


    /**
     * @param map 需要groupingIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除(逻辑删除)
     */
    @PutMapping(value = "/deleteGroupings")
    public ResultVo deleteGroupings(@RequestBody Map map) {
        // 获取数据
        List<Integer> groupingIds = (List<Integer>) map.get("groupingIds");
        String loginAdminName = (String) map.get("loginAdminName");
        try {
            sysGroupingService.updateByIds(groupingIds, loginAdminName);
            log.info("删除分组操作成功，删除的分组Id:{}", groupingIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(批删)，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }
    }
}

