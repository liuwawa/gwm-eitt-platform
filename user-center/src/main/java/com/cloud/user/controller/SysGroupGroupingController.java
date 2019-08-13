package com.cloud.user.controller;


import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.user.service.SysGroupGroupingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分组组织中间表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@RestController
@RequestMapping("/groupGrouping")
@Slf4j
@Api(value = "部门分组", tags = {"部门分组接口 SysGroupGroupingController"})
public class SysGroupGroupingController {
    @Autowired
    private SysGroupGroupingService sysGroupGroupingService;

    /**
     * @param map 添加的组织ids，和分组id
     * @return 操作结果
     * 添加或者编辑分组（如果存在则先删除所有存在的,再添加）
     */
    @PreAuthorize("hasAnyAuthority('back:group:delete','back:group:save','back:group:update')")
    @PostMapping("/updateGrouping")
    @ApiOperation(value = "添加部门分组", notes = "参数名称：（数组）groupIds，groupingId，groupingName，groupingRemark，loginAdminName")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo addGroupToGrouping(
            @ApiJsonObject(name = "添加部门分组", value = {
                    @ApiJsonProperty(key = "groupingId", example = "1", description = "groupingId"),
                    @ApiJsonProperty(key = "groupIds", example = "[]", description = "groupIds数组"),
                    @ApiJsonProperty(key = "groupingName", example = "groupingName", description = "groupingName"),
                    @ApiJsonProperty(key = "groupingRemark", example = "groupingRemark", description = "groupingRemark"),
                    @ApiJsonProperty(key = "loginAdminName", example = "loginAdminName", description = "loginAdminName"),
            })
            @RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        Integer groupingId = (Integer) map.get("groupingId");
        String groupingName = map.get("groupingName").toString();
        if ("".equals(groupingName)) {
            return new ResultVo(16630,
                    "请输入分组名称！", null);
        }
        String groupingRemark = map.get("groupingRemark").toString();
        if ("".equals(groupingRemark)) {
            return new ResultVo(16640,
                    "请输入分组详情！", null);
        }
        String loginAdminName = map.get("loginAdminName").toString();
        try {
            if (!sysGroupGroupingService.saveGroupToGrouping(groupIds, groupingId, groupingName, groupingRemark, loginAdminName)) {
                log.info("添加（编辑）组织到分组操作失败，添加（编辑）的分组id:{}", groupingId);
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code,
                        ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            log.info("添加（编辑）组织到分组操作成功，添加（编辑）的分组id:{}", groupingId);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加（编辑）组织到分组,出现异常!", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, e.getMessage(), null);
        }
    }


}

