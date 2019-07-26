package com.cloud.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;
import com.cloud.user.service.SysPermissionService;
import com.cloud.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@Api(value = "角色", tags = {"角色操作接口 SysRoleController"})
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 管理后台添加角色
     *
     * @param sysRole
     */
//	@LogAnnotation(module = LogModule.ADD_ROLE)
//	@PreAuthorize("hasAuthority('back:role:save')")
//	@PostMapping("/roles")
//	public SysRole save(@RequestBody SysRole sysRole) {
//		saveRole(sysRole);
//		return sysRole;
//	}

    /**
     * 管理后台添加角色(element ui)
     *
     * @param sysRole
     */
    @LogAnnotation(module = LogModule.ADD_ROLE)
    @PreAuthorize("hasAuthority('back:role:save')")
    @PostMapping("/roles2")
    @ApiOperation(value = "管理后台添加角色")
    public ResultVo save2(@RequestBody SysRole sysRole) {
        saveRole(sysRole);
        return ResultVo.builder().data(sysRole).code(200).msg("添加成功!").build();
    }

    /**
     * 保存角色
     *
     * @param sysRole
     */
    private void saveRole(@RequestBody SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getCode())) {
            throw new IllegalArgumentException("角色code不能为空");
        }
        if (StringUtils.isBlank(sysRole.getName())) {
            sysRole.setName(sysRole.getCode());
        }
        sysRole.setCreateTime(new Date());
        sysRoleService.saveOrUpdate(sysRole);
    }

    /**
     * 管理后台删除角色
     *
     * @param id
     */
//	@LogAnnotation(module = LogModule.DELETE_ROLE)
//	@PreAuthorize("hasAuthority('back:role:delete')")
//	@DeleteMapping("/roles/{id}")
//	public void deleteRole(@PathVariable Long id) {
//		sysRoleService.deleteRole(id);
//	}

    /**
     * 管理后台删除角色(element ui)
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:role:delete')")
    @DeleteMapping("/roles2/{id}")
    @ApiOperation(value = "管理后台删除角色")
    public ResultVo deleteRole2(@PathVariable Long id) {
        try {
            sysRoleService.deleteRole(id);
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            return ResultVo.builder().msg("删除失败").data(null).code(200).build();
        }
    }

    /**
     * 管理后台修改角色
     *
     * @param sysRole
     */
    @LogAnnotation(module = LogModule.UPDATE_ROLE)
    @PreAuthorize("hasAuthority('back:role:update')")
    @PutMapping("/roles")
    @ApiOperation(value = "管理后台修改角色")
    public SysRole update(@RequestBody SysRole sysRole) {
        if (StringUtils.isBlank(sysRole.getName())) {
            throw new IllegalArgumentException("角色名不能为空");
        }

        sysRoleService.update(sysRole);

        return sysRole;
    }

    /**
     * 管理后台给角色分配权限
     *
     * @param id            角色id
     * @param permissionIds 权限ids
     */
//	@LogAnnotation(module = LogModule.SET_PERMISSION)
//	@PreAuthorize("hasAuthority('back:role:permission:set')")
//	@PostMapping("/roles/{id}/permissions")
//	public void setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
//		sysRoleService.setPermissionToRole(id, permissionIds);
//	}

    /**
     * 管理后台给角色分配权限(element ui)
     *
     * @param map 角色id和权限id的集合
     */
    @LogAnnotation(module = LogModule.SET_PERMISSION)
    @PreAuthorize("hasAuthority('back:role:permission:set')")
    @PostMapping("/setPermission2Role")
    @ApiOperation(value = "管理后台给角色分配权限", notes = "参数：roleId,permissions（权限id数组）")
    public ResultVo setPermission2Role(
            @ApiJsonObject(name = "管理后台给角色分配权限", value = {
                    @ApiJsonProperty(key = "permissions", example = "[]", description = "permissions"),
                    @ApiJsonProperty(key = "roleId", example = "0", description = "roleId")
            })
            @RequestBody Map<String, Object> map) {
        try {
            Long roleId = Long.valueOf(map.get("roleId").toString());
            HashSet<Long> permissionIds = new HashSet<>(JSONArray.parseArray(map.get("permissions").toString(), Long.class));
            sysRoleService.setPermissionToRole(roleId, permissionIds);
            return ResultVo.builder().code(200).build();
        } catch (Exception e) {
            log.info(e + "");
            return ResultVo.builder().code(5000).msg("请联系管理员!").build();
        }

    }

    /**
     * 获取角色的权限
     *
     * @param id
     */
//	@PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
//	@GetMapping("/roles/{id}/permissions")
//	public Set<SysPermission> findPermissionsByRoleId(@PathVariable Long id) {
//		return sysRoleService.findPermissionsByRoleId(id);
//	}

    /**
     * 获取角色的权限(element ui)
     *
     * @param id
     */
    @PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
    @PostMapping("/queryPermissionsByRoleId")
    @ApiOperation(value = "获取角色的权限")
    public ResultVo queryPermissionsByRoleId(@ApiParam(value = "角色id", required = true) Long id) {
        Set<SysPermission> permissionsOfRole = sysRoleService.findPermissionsByRoleId(id);
        List<SysPermission> list = sysPermissionService.list();
        list.forEach(i -> {
            if (permissionsOfRole.contains(i)) {
                i.setChecked(true);
            }
        });
        return ResultVo.builder().code(200).msg("成功!").data(list).build();
    }

    @PreAuthorize("hasAuthority('back:role:query')")
    @GetMapping("/roles/{id}")
    public SysRole findById(@PathVariable Long id) {
        return sysRoleService.findById(id);
    }

    /**
     * 搜索角色
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:role:query')")
    @GetMapping("/roles")
    @ApiOperation(value = "搜索角色")
    public Page<SysRole> findRoles(@RequestParam Map<String, Object> params) {
        return sysRoleService.findRoles(params);
    }

    /**
     * 分页查询(element ui)
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:role:query')")
    @PostMapping("/findPage")
    @ApiOperation(value = "分页多条件查询", notes = "参数：pageNum，pageSize，condition（SysRole的name）")
    public PageResult findPage(
            @ApiJsonObject(name = "分页多条件查询角色", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "condition", example = "name", description = "name")})
            @RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String condition = String.valueOf(params.get("condition").toString());
        IPage<SysRole> roleIPage = sysRoleService.
                page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                        new QueryWrapper<SysRole>().like("name", condition));
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;
        Set<SysRole> roles = loginAppUser.getSysRoles();
        for (SysRole role : roles) {
            if (!SysConstants.ADMIN_CODE.equals(role.getCode())) { //当前用户无超级管理员角色,剔除超级管理员角色数据
                List<SysRole> records = roleIPage.getRecords();
                for (int i = 0; i < records.size(); i++) {
                    if (SysConstants.ADMIN_CODE.equals(records.get(i).getCode())) {
                        records.remove(i);
                    }
                }
            }
        }
        return PageResult.builder().content(roleIPage.getRecords()).
                pageNum(roleIPage.getCurrent()).
                pageSize(roleIPage.getSize()).
                totalPages(roleIPage.getPages()).
                totalSize(roleIPage.getTotal()).build();
    }

}
