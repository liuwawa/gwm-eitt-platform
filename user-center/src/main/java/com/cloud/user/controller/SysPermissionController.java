package com.cloud.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.enums.ResponseStatus;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.SysPermission;
import com.cloud.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 管理后台添加权限
     *
     * @param sysPermission
     * @return
     */
    @LogAnnotation(module = LogModule.ADD_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:save')")
    @PostMapping("/permissions")
    public ResultVo save(@RequestBody SysPermission sysPermission) {
        try {
            if (StringUtils.isBlank(sysPermission.getPermission())) {
                return new ResultVo(500, "权限标识不能为空", null);
            }
            if (StringUtils.isBlank(sysPermission.getName())) {
                return new ResultVo(500, "权限名不能为空", null);
            }
            SysPermission permission = sysPermissionService.getOne(new QueryWrapper<SysPermission>().lambda()
                    .eq(SysPermission::getPermission, sysPermission.getPermission()));
            if (permission != null) {
                return new ResultVo(500, "已经存在权限标识:" + permission.getPermission(), null);
            }
            sysPermission.setCreateTime(new Date());
            sysPermissionService.save(sysPermission);
            log.info("添加成功，权限名:{}", sysPermission.getName());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
    }

    /**
     * 管理后台修改权限
     *
     * @param sysPermission
     */
    @LogAnnotation(module = LogModule.UPDATE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:update')")
    @PutMapping("/permissions")
    public ResultVo update(@RequestBody SysPermission sysPermission) {
        try {
            if (StringUtils.isBlank(sysPermission.getName())) {
                throw new IllegalArgumentException("权限名不能为空");
            }
            SysPermission permission = sysPermissionService.getOne(new QueryWrapper<SysPermission>().lambda()
                    .eq(SysPermission::getPermission, sysPermission.getPermission()));
            if (permission != null) {
                return new ResultVo(500, "已经存在权限标识:" + permission.getPermission(), null);
            }
            sysPermissionService.update(sysPermission);
            log.info("编辑成功，权限id:{}", sysPermission.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("编辑出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }

    }

    /**
     * 删除权限标识
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:delete')")
    @DeleteMapping("/permissions/{id}")
    public ResultVo delete(@PathVariable Long id) {
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
     * 查询所有的权限标识
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findPages")
    public PageResult findPermissions(@RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String name = String.valueOf(params.get("name").toString());
        String permission = String.valueOf(params.get("permission").toString());
        IPage<SysPermission> permissionIPage = sysPermissionService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                new QueryWrapper<SysPermission>().lambda()
                        .like(SysPermission::getName, name)
                        .like(SysPermission::getPermission, permission));
        return PageResult.builder().content(permissionIPage.getRecords()).
                pageNum(permissionIPage.getCurrent()).
                pageSize(permissionIPage.getSize()).
                totalPages(permissionIPage.getPages()).
                totalSize(permissionIPage.getTotal()).build();
    }
}
