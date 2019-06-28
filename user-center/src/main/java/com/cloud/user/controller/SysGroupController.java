package com.cloud.user.controller;

import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.response.BaseEntity;
import com.cloud.response.ObjectConversionEntityUtil;
import com.cloud.user.service.SysGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/group")
@Slf4j
public class SysGroupController {
    @Autowired
    private SysGroupService sysGroupService;


    /**
     * @param baseEntity 封装组织的数据
     * @return 操作结果
     * 新建一个组织
     */
    @PostMapping("/saveGroup")
    public ResultVo saveGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);
        try {
            if (!sysGroupService.saveGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，添加的组织名称:{}", sysGroup.getGroupName());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            log.info("操作成功，添加的组织名称:{}", sysGroup.getGroupName());
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加组织，出现异常！",e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }

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
            log.error("根据id查询组织，出现异常！",e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }

    /**
     * @param baseEntity 主表和拓展表的数据
     * @return 修改结果
     * 修改组织和其拓展信息
     */
    @PutMapping("/updateGroup")
    public ResultVo updateGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);
        sysGroup.setUpdateTime(new Date());
        sysGroup.setUpdateBy(sysGroup.getLoginAdminName());
        try {
            if (!sysGroupService.updateGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，修改的组织名称:{}", sysGroup.getGroupName());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("修改组织，出现异常！",e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，修改的组织名称:{}", sysGroup.getGroupName());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

    /**
     * @param baseEntity 需要删除的组织
     * @return 操作结果
     * 组织单删(逻辑删除)
     */
    @PutMapping("/deleteGroup")
    public ResultVo deleteGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        try {
            if (!sysGroupService.updateById(sysGroup)) {
                log.info("操作失败，删除的组织id:{}", sysGroup.getGroupId());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("删除组织，出现异常！",e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，删除的组织id:{}", sysGroup.getGroupId());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

    /**
     * @param map 需要的数据groupIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除组织(逻辑删除)
     */
    @PutMapping("/deleteGroups")
    public ResultVo deleteGroups(@RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        String loginAdminName = (String) map.get("loginAdminName");
        try {
            sysGroupService.updateByIds(groupIds, loginAdminName);
            log.info("删除分组操作成功，删除的分组Id:{}", groupIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(批删)，出现异常！",e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }



   /* @GetMapping(value = "/allGroup")
    public Page<SysGroup> selectAllGrouping() {
        int count = sysGroupService.count();
        log.info("总条数:{}", count);
        return new Page<SysGroup>(count, sysGroupService.list(new QueryWrapper<SysGroup>()
                .eq("isDel", 0).orderByAsc("groupingShowOrder")));
    }*/


}

