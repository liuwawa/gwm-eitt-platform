package com.cloud.user.controller;

import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.user.service.SysGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 组织表 前端控制器
 * </p>
 *
 * @author wmy
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/group")
@Slf4j
public class SysGroupController {
    @Autowired
    private SysGroupService sysGroupService;


    /**
     * @param groupWithExpand 主表和拓展表的数据
     * @return 保存结果
     * 保存完整的一个组织
     */
    @PostMapping("/saveGroup")
    public ResultVo saveGroup(@RequestBody GroupWithExpand groupWithExpand) {
        SysGroup sysGroup = groupWithExpand.getSysGroup();
        SysGroupExpand sysGroupExpand = groupWithExpand.getSysGroupExpand();
        try {
            if (!sysGroupService.saveGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，添加的组织名称:{}", sysGroup.getGroupName());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("添加组织，出现异常！");
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，添加的组织名称:{}", sysGroup.getGroupName());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

    /**
     * @param groupId 组织id
     * @return 查询结果
     * 根据组织id查询详细数据
     */
    @GetMapping("/findGroup/{groupId}")
    public ResultVo<GroupWithExpand> findGroupById(@PathVariable Integer groupId) {
        try {
            GroupWithExpand groupWithExpand = sysGroupService.selectByGroupId(groupId);
            log.info("根据id查找组织成功，查找id:{}", groupId);
            return new ResultVo<GroupWithExpand>(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, groupWithExpand);
        } catch (Exception e) {
            log.error("根据id查询组织，出现异常！");
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }

    /**
     * @param groupWithExpand 主表和拓展表的数据
     * @return 修改结果
     * 修改组织和其拓展信息
     */
    @PutMapping("/updateGroup")
    public ResultVo updateGroup(@RequestBody GroupWithExpand groupWithExpand) {
        SysGroup sysGroup = groupWithExpand.getSysGroup();
        SysGroupExpand sysGroupExpand = groupWithExpand.getSysGroupExpand();
        try {
            if (!sysGroupService.updateGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，修改的组织名称:{}", sysGroup.getGroupName());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("修改组织，出现异常！");
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，修改的组织名称:{}", sysGroup.getGroupName());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

}

