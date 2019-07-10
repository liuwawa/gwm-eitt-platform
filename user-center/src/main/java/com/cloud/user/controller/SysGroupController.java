package com.cloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.GroupWithExpand;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.response.BaseEntity;
import com.cloud.response.ObjectConversionEntityUtil;
import com.cloud.user.service.SysGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @LogAnnotation(module = LogModule.ADD_GROUP)
    @PreAuthorize("hasAuthority('back:group:save')")
    @PostMapping("/saveGroup")
    public ResultVo saveGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);
        try {
            if (!sysGroupService.saveGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，添加的组织名称:{}", sysGroup.getLabel());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            log.info("操作成功，添加的组织名称:{}", sysGroup.getLabel());
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }

    }

    /**
     * @param groupId 组织id
     * @return 查询结果
     * 根据组织id查询详细数据
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping("/findGroup/{groupId}")
    public ResultVo<GroupWithExpand> findGroupById(@PathVariable Integer groupId) {
        try {
            GroupWithExpand groupWithExpand = sysGroupService.selectByGroupId(groupId);
            log.info("根据id查找组织成功，查找id:{}", groupId);
            return new ResultVo<GroupWithExpand>(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, groupWithExpand);
        } catch (Exception e) {
            log.error("根据id查询组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }

    /**
     * @param baseEntity 主表和拓展表的数据
     * @return 修改结果
     * 修改组织和其拓展信息
     */
    @LogAnnotation(module = LogModule.UPDATE_GROUP)
    @PreAuthorize("hasAuthority('back:group:update')")
    @PutMapping("/updateGroup")
    public ResultVo updateGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);
        sysGroup.setUpdateTime(new Date());
        sysGroup.setUpdateBy(sysGroup.getLoginAdminName());
        sysGroup.setIsUpdate("1");
        try {
            if (!sysGroupService.updateGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，修改的组织名称:{}", sysGroup.getLabel());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("修改组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，修改的组织名称:{}", sysGroup.getLabel());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

    /**
     * @param baseEntity 需要删除的组织
     * @return 操作结果
     * 组织单删(逻辑删除)
     */
    @LogAnnotation(module = LogModule.DELETE_GROUP)
    @PreAuthorize("hasAuthority('back:group:delete')")
    @PutMapping("/deleteGroup")
    public ResultVo deleteGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        try {
            if (!sysGroupService.updateById(sysGroup)) {
                log.info("操作失败，删除的组织id:{}", sysGroup.getId());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("删除组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
        log.info("操作成功，删除的组织id:{}", sysGroup.getId());
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
    }

    /**
     * @param map 需要的数据groupIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除组织(逻辑删除)
     */
    @PreAuthorize("hasAuthority('back:group:delete')")
    @PutMapping("/deleteGroups")
    public ResultVo deleteGroups(@RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        String loginAdminName = (String) map.get("loginAdminName");
        try {
            sysGroupService.updateByIds(groupIds, loginAdminName);
            log.info("删除分组操作成功，删除的分组Id:{}", groupIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(批删)，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.message, null);
        }
    }


    /**
     * @return 获取所有组织
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping("/getAllGroup")
    public Map getAllGroup() {

        List<SysGroup> groupList = sysGroupService.list(new QueryWrapper<SysGroup>().lambda()
                .eq(SysGroup::getIsDel, "0")
                .orderByAsc(SysGroup::getGroupShowOrder));
        List<SysGroup> firstLevelMenus;
        HashMap<Object, Object> reslut = new HashMap<>();
        firstLevelMenus = groupList.stream().filter(m -> m.getParentid().equals(0))
                .collect(Collectors.toList());
        firstLevelMenus.forEach(m -> setChildren(m, groupList));
        reslut.put("code", 200);
        reslut.put("msg", null);
        reslut.put("data", firstLevelMenus);
        return reslut;
    }

    /**
     * element  ui  数据
     *
     * @param sysGroup
     * @param sysGroupList
     */
    private void setChildren(SysGroup sysGroup, List<SysGroup> sysGroupList) {
        List<SysGroup> child = sysGroupList.stream().filter(m -> m.getParentid().equals(sysGroup.getId()))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(child)) {
            sysGroup.setChildren(child);
            //递归设置子元素，多级菜单支持
            child.parallelStream().forEach(c -> {
                setChildren(c, sysGroupList);
            });
        }
    }

    /**
     * @param groupId 组织的id
     * @return 查询结果
     * 根据组织的id查询其下属组织
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping("/getGroupsByGroupId/{groupId}")
    public ResultVo<SysGroup> getGroupsByGroupId(@PathVariable Integer groupId) {
        List<SysGroup> list = sysGroupService.list(new QueryWrapper<SysGroup>().lambda()
                .select(SysGroup::getId, SysGroup::getLabel)
                .eq(SysGroup::getIsDel, "0")
                .eq(SysGroup::getParentid, groupId)
                .orderByAsc(SysGroup::getGroupShowOrder));
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, list);

    }

}

