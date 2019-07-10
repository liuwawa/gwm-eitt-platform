package com.cloud.user.controller;


import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.service.SysUserGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class SysUserGroupingController {
    @Autowired
    private SysUserGroupingService sysUserGroupingService;

    /**
     * @param map 用于接收userId和groupingIds
     * @return 操作结果
     * 添加用户的可以查看的分组
     */
    @PostMapping("/addUserCheck")
    public ResultVo addUserGroupingCheck(@RequestBody Map map) {
        List<Integer> groupingIds = (List<Integer>) map.get("groupingIds");
        Integer userId = (Integer) map.get("userId");
        try {
            if (!sysUserGroupingService.saveUserCheck(groupingIds, userId)) {
                log.info("添加用户查看分组，操作失败!添加的用户id:{}", userId);
                return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
            }
            log.info("添加用户查看分组，操作成功!添加的用户id:{}", userId);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.info("添加用户查看分组，出现异常!", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_ERROR.message, null);
        }
    }

    /**
     * @param userId 用户id
     * @return 查询结果
     * 根据userId查找该用户可以查看的分组
     */
    @GetMapping("/getGroupings/{userId}")
    public ResultVo<List<SysGrouping>> getGroupingsByUserId(@PathVariable Integer userId) {

        List<SysGrouping> groupings = sysUserGroupingService.getGroupingsByUserId(userId);
        // 去除空的分组
        groupings = groupings.stream().filter(m -> m.getChildren() != null)
                .collect(Collectors.toList());

        log.info("根据userId查找可以查看的所有grouping,用户的id:{}", userId);
        return new ResultVo<List<SysGrouping>>(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, groupings);

    }
}

