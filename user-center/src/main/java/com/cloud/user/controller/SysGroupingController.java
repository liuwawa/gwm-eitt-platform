package com.cloud.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * @return 总个数和结果
     * 查询所有的分组数据
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping(value = "/allGrouping")
    public Page<SysGrouping> selectAllGrouping() {
        int count = sysGroupingService.count();
        log.info("总条数:{}", count);
        return new Page<SysGrouping>(count, sysGroupingService.list(new QueryWrapper<SysGrouping>().lambda()
                .eq(SysGrouping::getIsDel, 0)
                .orderByAsc(SysGrouping::getGroupingShowOrder)));
    }


    /**
     * @param map 需要groupingIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除(逻辑删除)
     */
    @LogAnnotation(module = LogModule.DELETE_GROUPING)
    @PreAuthorize("hasAuthority('back:group:delete')")
    @DeleteMapping(value = "/deleteGroupings")
    public ResultVo deleteGroupings(@RequestBody Map map) {
        // 获取数据
        List<Integer> groupingIds = (List<Integer>) map.get("groupingIds");
        String loginAdminName = map.get("loginAdminName").toString();
        try {
            sysGroupingService.updateByIds(groupingIds, loginAdminName);
            log.info("删除分组操作成功，删除的分组Id:{}", groupingIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(批删)，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, e.getMessage(), null);
        }
    }

    /**
     * 生成一个分组的同时把组织添加到这个分组里
     *
     * @param map 需要添加的gorupId和新增的grouping的数据
     * @return 操作结果
     */
    @LogAnnotation(module = LogModule.ADD_GROUPING)
    @PreAuthorize("hasAuthority('back:group:save')")
    @PostMapping("/initGroupingSaveGroup")
    public ResultVo initGroupingSaveGroup(@RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("ids");
        String gorupingName = map.get("name").toString();
        String groupingRemark = map.get("remark").toString();
        String loginAdminName = map.get("userName").toString();
        SysGrouping grouping = SysGrouping.builder()
                .groupingName(gorupingName)
                .groupingRemark(groupingRemark)
                .createBy(loginAdminName)
                .createTime(new Date()).build();
        if (gorupingName == null) {
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code).msg("分组名不能为空").data(null).build();
        }
        try {
            if (!sysGroupingService.initGroupingSaveGroup(groupIds, grouping)) {
                log.info("生成分组和添加组织到分组失败,分组名:{}", gorupingName);
                return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code).msg(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message).data(null).build();
            }
            log.info("生成分组和添加组织到分组成功,分组名:{}", gorupingName);
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code).msg(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message).data(null).build();
        } catch (Exception e) {
            log.error("生成分组和添加组织到分组出现异常", e);
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code).msg(e.getMessage()).data(null).build();
        }

    }
}

