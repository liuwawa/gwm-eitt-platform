package com.gwm.one.hr.user.controller;


import com.gwm.one.common.plugins.ApiJsonObject;
import com.gwm.one.common.plugins.ApiJsonProperty;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.enums.ResponseStatus;
import com.gwm.one.hr.user.service.SysGroupingService;
import com.gwm.one.model.log.LogAnnotation;
import com.gwm.one.model.log.constants.LogModule;
import com.gwm.one.model.hr.user.SysGrouping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "组织分组", tags = {"分组操作接口 SysGroupingController"})
public class SysGroupingController {
    @Autowired
    private SysGroupingService sysGroupingService;

    /**
     * @return 总个数和结果
     * 查询所有的分组数据
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping(value = "/allGrouping")
    @ApiOperation(value = "分页获取全部分组")
    public ResultVo selectAllGrouping() {
        ResultVo resultVo = ResultVo.builder().code(200).msg("操作成功！").build();
        List<SysGrouping> allCheckedGrouping = sysGroupingService.findAllCheckedGrouping();
        resultVo.setData(allCheckedGrouping);
        return resultVo;
    }


    /**
     * @param map 需要groupingIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除(逻辑删除)
     */
    @LogAnnotation(module = LogModule.DELETE_GROUPING)
    @PreAuthorize("hasAuthority('back:group:delete')")
    @DeleteMapping(value = "/deleteGroupings")
    @ApiOperation(value = "批量删除(逻辑删除)", notes = "参数：groupingIds（数组），loginAdminName")
    public ResultVo deleteGroupings(
            @ApiJsonObject(name = "批量删除分组", value = {
                    @ApiJsonProperty(key = "groupingIds", example = "[]", description = "groupingIds"),
                    @ApiJsonProperty(key = "loginAdminName", example = "", description = "loginAdminName")
            })
            @RequestBody Map map) {
        // 获取数据
        List<Integer> groupingIds = (List<Integer>) map.get("groupingIds");
        String loginAdminName = map.get("loginAdminName").toString();
        try {
            sysGroupingService.updateByIds(groupingIds, loginAdminName);
            log.info("删除分组操作成功，删除的分组Id:{}", groupingIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
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
    @ApiOperation(value = "生成一个分组的同时把组织添加到这个分组里", notes = "参数：ids（数组），name，remark，userName")
    public ResultVo initGroupingSaveGroup(
            @ApiJsonObject(name = "生成一个分组的同时把组织添加到这个分组里", value = {
                    @ApiJsonProperty(key = "ids", example = "[]", description = "ids"),
                    @ApiJsonProperty(key = "name", example = "name", description = "name"),
                    @ApiJsonProperty(key = "remark", example = "remark", description = "remark"),
                    @ApiJsonProperty(key = "userName", example = "userName", description = "userName")
            })
            @RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("ids");
        String gorupingName = map.get("name").toString();
        String groupingRemark = map.get("remark").toString();
        String loginAdminName = map.get("userName").toString();
        SysGrouping grouping = SysGrouping.builder()
                .groupingName(gorupingName)
                .groupingRemark(groupingRemark)
                .createBy(loginAdminName)
                .createTime(new Date()).build();
        if (null == gorupingName) {
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code).msg("分组名不能为空").data(null).build();
        }
        try {
            if (!sysGroupingService.initGroupingSaveGroup(groupIds, grouping)) {
                log.info("生成分组和添加组织到分组失败,分组名:{}", gorupingName);
                return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code)
                        .msg(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message).data(null).build();
            }
            log.info("生成分组和添加组织到分组成功,分组名:{}", gorupingName);
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code)
                    .msg(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message).data(null).build();
        } catch (Exception e) {
            log.error("生成分组和添加组织到分组出现异常", e);
            return ResultVo.builder().code(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code).msg(e.getMessage()).data(null).build();
        }

    }
}

