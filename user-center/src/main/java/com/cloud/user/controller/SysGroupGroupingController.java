package com.cloud.user.controller;


import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.user.SysGroup;
import com.cloud.user.service.SysGroupGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResultVo addGroupToGrouping(@RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        Integer groupingId = (Integer) map.get("groupingId");
        String groupingName = map.get("groupingName").toString();
        String groupingRemark = map.get("groupingRemark").toString();
        String loginAdminName = map.get("loginAdminName").toString();
        try {
            if (!sysGroupGroupingService.saveGroupToGrouping(groupIds, groupingId, groupingName, groupingRemark, loginAdminName)) {
                log.info("添加组织到分组操作失败，添加的分组id:{}", groupingId);
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            log.info("添加组织到分组操作成功，添加的分组id:{}", groupingId);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加组织到分组,出现异常!", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }


}

