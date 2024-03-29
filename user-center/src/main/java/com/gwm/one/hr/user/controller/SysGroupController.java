package com.gwm.one.hr.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gwm.one.common.plugins.ApiJsonObject;
import com.gwm.one.common.plugins.ApiJsonProperty;
import com.gwm.one.common.utils.RedisUtils;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.enums.ResponseStatus;
import com.gwm.one.hr.user.service.SysGroupService;
import com.gwm.one.hr.user.service.SysUserService;
import com.gwm.one.hr.user.test.GZIPUtils;
import com.gwm.one.model.log.LogAnnotation;
import com.gwm.one.model.log.constants.LogModule;
import com.gwm.one.model.hr.user.SysGroup;
import com.gwm.one.model.hr.user.SysGroupExpand;
import com.gwm.one.model.hr.user.constants.SysUserResponse;
import com.gwm.one.response.BaseEntity;
import com.gwm.one.response.ObjectConversionEntityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
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
@Api(value = "部门", tags = {"部门操作接口 SysGroupController"})
public class SysGroupController {
    @Autowired
    private SysGroupService sysGroupService;
    @Autowired
    private SysUserService appUserService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @param baseEntity 封装组织的数据
     * @return 操作结果
     * 新建一个组织
     */
    @LogAnnotation(module = LogModule.ADD_GROUP)
    @PreAuthorize("hasAuthority('back:group:save')")
    @PostMapping("/saveGroup")
    @ApiOperation(value = "添加部门")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo saveGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);


        try {
            if (!sysGroupService.saveGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，添加的组织名称:{}", sysGroup.getLabel());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code,
                        ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            // 查出数据库中的所有数据并放入redis
            getAllGroupsFromDB();
            log.info("操作成功，添加的组织名称:{}", sysGroup.getLabel());
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, e.getMessage(), null);
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
    @ApiOperation(value = "修改部门")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo updateGroup(@RequestBody BaseEntity baseEntity) {
        SysGroup sysGroup = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroup.class);
        SysGroupExpand sysGroupExpand = ObjectConversionEntityUtil.getBaseData(baseEntity, SysGroupExpand.class);
        sysGroup.setUpdateTime(new Date());
        sysGroup.setUpdateBy(sysGroup.getLoginAdminName());
        sysGroup.setIsDel("0");
        sysGroup.setIsUpdate("1");
        try {
            if (!sysGroupService.updateGroupAndGroupExpand(sysGroup, sysGroupExpand)) {
                log.info("操作失败，修改的组织名称:{}", sysGroup.getLabel());
                return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.code,
                        ResponseStatus.RESPONSE_GROUP_HANDLE_FAILED.message, null);
            }
            // 查出数据库中的所有数据并放入redis
            getAllGroupsFromDB();
            log.info("操作成功，修改的组织名称:{}", sysGroup.getLabel());
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("修改组织，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, e.getMessage(), null);
        }

    }

    /**
     * @param map 需要的数据groupIds（List），loginAdminName（String）
     * @return 操作结果
     * 批量删除组织(逻辑删除)
     */
    @LogAnnotation(module = LogModule.DELETE_GROUP)
    @PreAuthorize("hasAuthority('back:group:delete')")
    @DeleteMapping("/deleteGroups")
    @ApiOperation(value = "批量删除部门", notes = "参数：（数组）groupIds，loginAdminName")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo deleteGroups(@ApiJsonObject(name = "批量删除部门", value = {
            @ApiJsonProperty(key = "groupIds", example = "[]", description = "groupIds"),
            @ApiJsonProperty(key = "loginAdminName", example = "", description = "loginAdminName")
    }) @RequestBody Map map) {
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        String loginAdminName = (String) map.get("loginAdminName");
        try {
            sysGroupService.updateByIds(groupIds, loginAdminName);
            // 查出数据库中的所有数据并放入redis
            getAllGroupsFromDB();
            log.info("删除分组操作成功，删除的分组Id:{}", groupIds);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除分组(批删)，出现异常！", e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, e.getMessage(), null);
        }
    }

    /**
     * @return 获取所有组织
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping("/getAllGroup")
    @ApiOperation(value = "获取全部部门")
    public ResultVo getAllGroup() {
        // 从Redis去取
        List<SysGroup> groupsFromRedis = getGroupsFromRedis();
        if (null != groupsFromRedis) {
            return ResultVo.builder().code(200).msg("操作成功！").data(groupsFromRedis).build();
        }
        // 查出数据库中的所有数据并放入redis
        List<SysGroup> allGroupsFromDB = getAllGroupsFromDB();
        return ResultVo.builder().code(200).msg("操作成功！").data(allGroupsFromDB).build();
    }


    /**
     * @return 点击获取分组的详细信息
     */
    @GetMapping("/getGroupInfo")
    @ApiOperation(value = "点击获取分组的详细信息")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo getGroupInfoById(Integer groupId) {
        // 根据id查询出分组
        SysGroup group = SysGroup.builder().id(groupId).build();
        SysGroup sysGroup = group.selectOne(new QueryWrapper<SysGroup>().lambda().eq(SysGroup::getId, groupId).eq(SysGroup::getIsDel, "0"));
        if (null == sysGroup) {
            return new ResultVo(16610, "没有找到该分组信息，请查看是否已经删除！", null);
        }
        // 查找其拓展数据
        SysGroupExpand sysGroupExpand = SysGroupExpand.builder().build();
        SysGroupExpand expand = sysGroupExpand.selectOne(
                new QueryWrapper<SysGroupExpand>().lambda().eq(SysGroupExpand::getGroupId, groupId));
        // 设置前台需要的拓展属性
        if (null != expand) {
            sysGroup.setGDirectLeader(expand.getGDirectLeader());
            sysGroup.setGDeptopLeader(expand.getGDeptopLeader());
            sysGroup.setGUnittopLeader(expand.getGUnittopLeader());
            sysGroup.setGModule(expand.getGModule());
            sysGroup.setSubModule(expand.getSubModule());
            SysUserResponse directLeader = appUserService.getUsers(expand.getGDirectLeader());
            if (null != directLeader) {
                sysGroup.setGDirectLeaderInfo(directLeader);
            }
            SysUserResponse deptopLeader = appUserService.getUsers(expand.getGDeptopLeader());
            if (null != deptopLeader) {
                sysGroup.setGDeptopLeaderInfo(deptopLeader);
            }
            SysUserResponse unittopLeader = appUserService.getUsers(expand.getGUnittopLeader());
            if (null != unittopLeader) {
                sysGroup.setGUnittopLeaderInfo(unittopLeader);
            }
        }

        return new ResultVo(200, "操作成功！", sysGroup);
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
            //递归设置子元素
            child.parallelStream().forEach(c -> {
                setChildren(c, sysGroupList);
            });
        }
    }


    /**
     * 获取所有组织
     */
    @PreAuthorize("hasAuthority('back:group:query')")
    @GetMapping("/getAll")
    @ApiOperation(value = "按节点获取全部部门")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo<List<SysGroup>> getAll() {
        SysGroup sysGroup = SysGroup.builder().build();
        List<SysGroup> groups = sysGroup.selectAll();
        return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, groups);
    }


    /**
     * 修改组织结构的接口
     */
    @LogAnnotation(module = LogModule.UPDATE_GROUP)
    @PreAuthorize("hasAuthority('back:group:update')")
    @PostMapping("/changeGroup")
    @ApiOperation(value = "修改部门", notes = "参数：parentId，（数组）groupIds, loginAdminName")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo changeGroupStructure(
            @ApiJsonObject(name = "修改组织结构的接口", value = {
                    @ApiJsonProperty(key = "parentId", example = "1", description = "parentId"),
                    @ApiJsonProperty(key = "groupIds", example = "[]", description = "groupIds数组"),
                    @ApiJsonProperty(key = "loginAdminName", example = "admin", description = "操作人")
            })
            @RequestBody Map map) {
        if (null == map.get("parentId")) {
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        }
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        Integer parentId = Integer.valueOf(map.get("parentId").toString());
        String loginAdminName = map.get("loginAdminName").toString();
        try {
            sysGroupService.changeGroup(groupIds, parentId, loginAdminName);
            // 查出数据库中的所有数据并放入redis
            getAllGroupsFromDB();
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.code,
                    ResponseStatus.RESPONSE_GROUP_HANDLE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("出现异常" + e);
            return new ResultVo(ResponseStatus.RESPONSE_GROUP_HANDLE_ERROR.code, e.getMessage(), null);

        }
    }


    // 从redis取出数据
    public List<SysGroup> getGroupsFromRedis() {

        String allGroups = redisUtils.getString("allGroups");
        // 如果redis有 则解压缩 直接返回
        String uncompress = GZIPUtils.uncompress(allGroups);

        List<SysGroup> allOfGroups = null;

        try {
            if (null != allGroups) {
                allOfGroups = objectMapper.readValue(uncompress, new TypeReference<List<SysGroup>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allOfGroups;
    }

    // 从数据库拿出所有数据并放入redis的方法
    public List<SysGroup> getAllGroupsFromDB() {
        // 查找结果
        List<SysGroup> groupList = sysGroupService.list(new QueryWrapper<SysGroup>().lambda()
                .eq(SysGroup::getIsDel, "0")
                .orderByAsc(SysGroup::getGroupShowOrder));
        List<SysGroup> firstLevelMenus = groupList.stream().filter(m -> m.getParentid().equals(0))
                .collect(Collectors.toList());
        firstLevelMenus.forEach(m -> setChildren(m, groupList));
        // 转化字符串并压缩
        String groupsInfo = null;
        try {
            groupsInfo = objectMapper.writeValueAsString(firstLevelMenus);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String compressGroupInfo = GZIPUtils.compress(groupsInfo);
        redisUtils.addString("allGroups", compressGroupInfo, null);
        return firstLevelMenus;
    }

}

