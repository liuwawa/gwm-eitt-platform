package com.cloud.user.controller;

import java.util.*;

import com.cloud.common.utils.AppUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.common.Page;
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
//    @LogAnnotation(module = LogModule.ADD_PERMISSION)
//    @PreAuthorize("hasAuthority('back:permission:save')")
//    @PostMapping("/permissions")
//    public SysPermission save(@RequestBody SysPermission sysPermission) {
//        if (StringUtils.isBlank(sysPermission.getPermission())) {
//            throw new IllegalArgumentException("权限标识不能为空");
//        }
//        if (StringUtils.isBlank(sysPermission.getName())) {
//            throw new IllegalArgumentException("权限名不能为空");
//        }
//
//        sysPermissionService.save(sysPermission);
//
//        return sysPermission;
//    }

    /**
     * 管理后台添加权限( element ui)
     *
     * @param sysPermission
     * @return
     */
    @LogAnnotation(module = LogModule.ADD_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:save')")
    @PostMapping("/permissions2")
    public ResultVo save2(@RequestBody SysPermission sysPermission) {
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
//    @LogAnnotation(module = LogModule.UPDATE_PERMISSION)
//    @PreAuthorize("hasAuthority('back:permission:update')")
//    @PutMapping("/permissions")
//    public SysPermission update(@RequestBody SysPermission sysPermission) {
//        if (StringUtils.isBlank(sysPermission.getName())) {
//            throw new IllegalArgumentException("权限名不能为空");
//        }
//
//        sysPermissionService.update(sysPermission);
//
//		return sysPermission;
//	}

    /**
     * 管理后台修改权限（ element ui）
     *
     * @param sysPermission
     */
    @LogAnnotation(module = LogModule.UPDATE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:update')")
    @PutMapping("/permissions2")
    public ResultVo update2(@RequestBody SysPermission sysPermission) {
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
//    @LogAnnotation(module = LogModule.DELETE_PERMISSION)
//    @PreAuthorize("hasAuthority('back:permission:delete')")
//    @DeleteMapping("/permissions/{id}")
//    public void delete(@PathVariable Long id) {
//        sysPermissionService.delete(id);
//    }

    /**
     * 删除权限标识（  element ui）
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_PERMISSION)
    @PreAuthorize("hasAuthority('back:permission:delete')")
    @DeleteMapping("/permissions2/{id}")
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
     * 查询所有的权限标识
     */
//    @PreAuthorize("hasAuthority('back:permission:query')")
//    @GetMapping("/permissions")
//    public Page<SysPermission> findPermissions(@RequestParam Map<String, Object> params) {
//        return sysPermissionService.findPermissions(params);
//    }

    /**
     * 分页查询所有的权限标识分（ element ui）
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findPages")
    public PageResult findPermissionsPage(@RequestBody Map<String, Object> params) {
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

    /**
     * 查询所有的权限标识(element ui)
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findAllPermissions")
    public List<SysPermission> findAllPermissions() {
        //		for (SysPermission sysPermission : list) {
//		}
//        Set<String> permissions = Objects.requireNonNull(AppUserUtil.getLoginAppUser()).getPermissions();
//        List<SysPermission> list = sysPermissionService.list();
//        list.forEach(l -> {
//            if (permissions.contains(l.getPermission())) {
//                l.setChecked(true);
//            }
//        });
        return sysPermissionService.list();
    }

    /**
     * 查询用户的权限标识(element ui)
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @PostMapping("/findUserPermissions")
    public List<SysPermission> findUserPermissions() {
//		List<SysPermission> list = sysPermissionService.list();
//		for (SysPermission sysPermission : list) {
//		}
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
