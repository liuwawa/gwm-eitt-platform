package com.cloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.enums.ResponseStatus;
import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysPermission;
import com.cloud.user.service.SysPermissionService;
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
@Api(value = "权限接口", tags = {"权限操作接口 SysPermissionController"})
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 管理后台添加权限( element ui)
     *
     * @param sysPermission
     * @return
     */
    @LogAnnotation(module = LogModule.ADD_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:save')")
    @PostMapping("/permissions2")
    @ApiOperation(value = "添加权限")
    public ResultVo save2(@ApiParam(value = "SysPermission对象", required = true) @RequestBody SysPermission sysPermission) {
        try {
            if (StringUtils.isBlank(sysPermission.getPermission())) {
                return new ResultVo(500, "权限标识不能为空", null);
            }
            if (StringUtils.isBlank(sysPermission.getName())) {
                return new ResultVo(500, "权限名不能为空", null);
            }
            SysPermission permission = sysPermissionService.getOne(new QueryWrapper<SysPermission>().lambda()
                    .eq(SysPermission::getPermission, sysPermission.getPermission()));
            if (null != permission) {
                return new ResultVo(500, "已经存在权限标识:" + permission.getPermission(), null);
            }
            SysPermission sysPermission1 = sysPermissionService.getOne(new QueryWrapper<SysPermission>().lambda()
                    .eq(SysPermission::getName, sysPermission.getName()));
            if (null != sysPermission1) {
                return new ResultVo(500, "权限名称已经存在，添加失败！", null);
            }

            sysPermission.setCreateTime(new Date());
            sysPermissionService.save(sysPermission);
            //给超级管理员添加新增权限
            sysPermissionService.addPermissionToRole(SysConstants.ADMIN_ROLE_ID, sysPermission.getId());
            log.info("添加成功，权限名:{}", sysPermission.getName());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
    }

    /**
     * 管理后台修改权限（ element ui）
     *
     * @param sysPermission
     */
    @LogAnnotation(module = LogModule.UPDATE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:update')")
    @PutMapping("/permissions2")
    @ApiOperation(value = "管理后台修改权限")
    public ResultVo update2(@RequestBody SysPermission sysPermission) {
        try {
            if (StringUtils.isBlank(sysPermission.getName())) {
                throw new IllegalArgumentException("权限名不能为空");
            }
            sysPermissionService.update(sysPermission);
            log.info("编辑成功，权限id:{}", sysPermission.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("编辑出现异常", e);
            return new ResultVo(500, e.getMessage(), null);
        }

    }


    /**
     * 删除权限标识（  element ui）
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:delete')")
    @DeleteMapping("/permissions2/{id}")
    @ApiOperation(value = "删除权限标识")
    public ResultVo delete2(@PathVariable Long id) {
        try {
            sysPermissionService.delete(id);
            log.info("删除成功，权限id:{}", id);
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("删除出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }

    }

    /**
     * 分页查询所有的权限标识分（ element ui）
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findPages")
    @ApiOperation(value = "分页多条件查询", notes = "参数：pageNum，pageSize，name，permission（对象）")
    public PageResult findPermissionsPage(
            @ApiJsonObject(name = "分页多条件查询权限", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "name", example = "name", description = "name"),
                    @ApiJsonProperty(key = "permission", example = "permission", description = "permission")
            })
            @RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String name = String.valueOf(params.get("name").toString());
        String permission = String.valueOf(params.get("permission").toString());
        IPage<SysPermission> permissionIPage = sysPermissionService.
                page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                        new QueryWrapper<SysPermission>().lambda()
                                .like(SysPermission::getName, name)
                                .like(SysPermission::getPermission, permission)
                                .orderByDesc(SysPermission::getCreateTime));
        return PageResult.builder().content(permissionIPage.getRecords()).
                pageNum(permissionIPage.getCurrent()).
                pageSize(permissionIPage.getSize()).
                totalPages(permissionIPage.getPages()).
                totalSize(permissionIPage.getTotal()).build();
    }

    /**
     * 查询所有的权限标识(element ui)
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findAllPermissions")
    @ApiOperation(value = "查询所有的权限标识")
    public List<SysPermission> findAllPermissions() {
        return sysPermissionService.list();
    }

    /**
     * 查询用户的权限标识(element ui)
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findUserPermissions")
    @ApiOperation(value = "查询所有的权限标识")
    public List<SysPermission> findUserPermissions() {
        Set<String> permissions = Objects.requireNonNull(AppUserUtil.getLoginAppUser()).getPermissions();
        List<SysPermission> list = sysPermissionService.list();
        list.forEach(l -> {
            if (permissions.contains(l.getPermission())) {
                l.setChecked(true);
            }
        });
        return list;
    }
}
