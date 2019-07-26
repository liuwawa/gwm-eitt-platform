package com.cloud.user.controller;


import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.service.SysUserGroupingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户，分组中间表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-29
 */
@RestController
@RequestMapping("/userGrouping")
@Slf4j
@Api(value = "用户分组", tags = {"用户分组接口 SysUserGroupingController"})
public class SysUserGroupingController {
    @Autowired
    private SysUserGroupingService sysUserGroupingService;

    /**
     * @param map 用于接收userId和groupingIds
     * @return 操作结果
     * 添加用户的可以查看的分组
     */
    @PostMapping("/addUserCheck")
    @ApiOperation(value = "添加用户的可以查看的分组", notes = "参数：groupingIds（组织id集合）,userId")
    public ResultVo addUserGroupingCheck(
            @ApiJsonObject(name = "添加用户的可以查看的分组", value = {
                    @ApiJsonProperty(key = "groupingIds", example = "[]", description = "groupingIds"),
                    @ApiJsonProperty(key = "userId", example = "0", description = "userId")
            })
            @RequestBody Map map) {
        List<Integer> groupingIds = (List<Integer>) map.get("groupingIds");
        Integer userId = (Integer) map.get("userId");
        try {
            if (!sysUserGroupingService.saveUserCheck(groupingIds, userId)) {
                log.info("添加用户查看分组，操作失败!添加的用户id:{}", userId);
                return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code,
                        ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
            }
            log.info("添加用户查看分组，操作成功!添加的用户id:{}", userId);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.info("添加用户查看分组，出现异常!", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code,
                    ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }
    }

    /**
     * @param userId 用户id
     * @return 查询结果
     * 根据userId查找该用户可以查看的分组
     */
    @GetMapping("/getGroupings/{userId}")
    @ApiOperation(value = "根据userId查找该用户可以查看的分组")
    public ResultVo<List<SysGrouping>> getGroupingsByUserId(@PathVariable Integer userId) {

        List<SysGrouping> groupings = sysUserGroupingService.getGroupingsByUserId(userId);
        // 筛选有group的分组
        groupings = groupings.stream().filter(m -> m.getChildren() != null)
                .collect(Collectors.toList());

        log.info("根据userId查找可以查看的所有grouping,用户的id:{}", userId);
        return new ResultVo<List<SysGrouping>>(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, groupings);

    }
}

