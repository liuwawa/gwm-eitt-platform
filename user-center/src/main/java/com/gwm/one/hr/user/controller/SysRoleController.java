package com.gwm.one.hr.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gwm.one.common.constants.SysConstants;
import com.gwm.one.common.plugins.ApiJsonObject;
import com.gwm.one.common.plugins.ApiJsonProperty;
import com.gwm.one.common.utils.AppUserUtil;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.hr.user.service.SysPermissionService;
import com.gwm.one.hr.user.service.SysRoleService;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.common.PageResult;
import com.gwm.one.model.log.LogAnnotation;
import com.gwm.one.model.log.constants.LogModule;
import com.gwm.one.model.hr.user.LoginAppUser;
import com.gwm.one.model.hr.user.SysPermission;
import com.gwm.one.model.hr.user.SysRole;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Api(value = "角色", tags = {"角色操作接口 SysRoleController"})
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 管理后台添加角色(element ui)
     *
     * @param sysRole
     */
    @LogAnnotation(module = LogModule.ADD_ROLE)
    @PreAuthorize("hasAuthority('back:role:save')")
    @PostMapping("/roles2")
    @ApiOperation(value = "管理后台添加角色")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo save2(@RequestBody SysRole sysRole) {
        if ("超级管理员".equals(sysRole.getRoleType())) {
            sysRole.setRoleType(SysConstants.SUPER_ADMIN_ROLE_TYPE);
        }
        if ("普通管理员".equals(sysRole.getRoleType())) {
            sysRole.setRoleType(SysConstants.COMMON_ADMIN_ROLE_TYPE);
        }
        if ("普通用户".equals(sysRole.getRoleType())) {
            sysRole.setRoleType(SysConstants.COMMON_USER_ROLE_TYPE);
        }

        QueryWrapper<SysRole> sysRoleWrapper = new QueryWrapper<>();
        sysRoleWrapper.eq("code", sysRole.getCode())
                .and(i -> i.ne("id", sysRole.getId()));
        if (sysRoleService.getOne(sysRoleWrapper) != null) {
            return ResultVo.builder().data(sysRole).code(40000).msg("code已存在，不能重复.").build();
        }
        sysRoleWrapper = new QueryWrapper<>();
        sysRoleWrapper.eq("name", sysRole.getName())
                .and(i -> i.ne("id", sysRole.getId()));

        if (sysRoleService.getOne(sysRoleWrapper) != null) {
            return ResultVo.builder().data(sysRole).code(40001).msg("角色名已存在，不能重复.").build();
        }

        if (sysRole.getId() != 0) {
            //修改之前要先判断一下（不能修改本级）
            // 登录用户的角色类型
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            Set<SysRole> sysRoles = loginAppUser.getSysRoles();
            List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
            //被修改角色的角色类型
            SysRole updateRole = sysRoleService.findById(sysRole.getId());
            //如果被修改角色的角色类型==登录用户的角色类型，则不能修改
            if (list.get(0).equals(updateRole.getRoleType()) && !list.get(0).equals("1")) {
                return ResultVo.builder().msg("没有权限").data(null).code(500).build();
                //throw new IllegalArgumentException("没有权限");
            }
        }
        saveRole(sysRole);
        return ResultVo.builder().data(sysRole).code(200).msg("添加成功!").build();
    }


    /**
     * 保存角色
     *
     * @param sysRole
     */
    private void saveRole(@RequestBody SysRole sysRole) {
//        QueryWrapper<SysRole> sysRoleWrapper = new QueryWrapper<>();
//        sysRoleWrapper.eq("code", sysRole.getCode());
//        if (sysRoleService.getOne(sysRoleWrapper) != null) {
//            throw new IllegalArgumentException("code已存在，不能重复。");
//        }
//        sysRoleWrapper = new QueryWrapper<>();
//        sysRoleWrapper.eq("name", sysRole.getName());
//
//        if (sysRoleService.getOne(sysRoleWrapper) != null) {
//            throw new IllegalArgumentException("角色名已存在，不能重复。");
//        }
        if (StringUtils.isBlank(sysRole.getCode())) {
            throw new IllegalArgumentException("角色code不能为空");
        }
        if (StringUtils.isBlank(sysRole.getName())) {
            sysRole.setName(sysRole.getCode());
        }
        if (sysRole.getId() == 0) { //新增
            sysRole.setCreateTime(new Date());
        } else {

            sysRole.setUpdateTime(new Date());
        }

        try {
            sysRoleService.saveOrUpdate(sysRole);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 管理后台删除角色(element ui)
     *
     * @param id
     */
    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:role:delete')")
    @DeleteMapping("/roles2/{id}")
    @ApiOperation(value = "管理后台删除角色")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo deleteRole2(@PathVariable Long id) {
        try {
            //删除之前要先判断一下（不能删除本级）
            // 登录用户的角色类型
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            Set<SysRole> sysRoles = loginAppUser.getSysRoles();
            List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
            //被删除角色的角色类型
            SysRole delRole = sysRoleService.findById(id);
            //如果被删除角色的角色类型==登录用户的角色类型且不是超级管理员，则不能删除
            if (list.get(0).equals(delRole.getRoleType()) && !list.get(0).equals("1")) {
                return ResultVo.builder().msg("没有权限").data(null).code(500).build();
            }

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
     * 管理后台给角色分配权限(element ui)
     *
     * @param map 角色id和权限id的集合
     */
    @LogAnnotation(module = LogModule.SET_PERMISSION)
    @PreAuthorize("hasAuthority('back:role:permission:set')")
    @PostMapping("/setPermission2Role")
    @ApiOperation(value = "管理后台给角色分配权限", notes = "参数：roleId,permissions（权限id数组）")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
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
     * 获取角色的权限(element ui)
     *
     * @param id
     */
    @PreAuthorize("hasAnyAuthority('back:role:permission:set')")
    @PostMapping("/queryPermissionsByRoleId")
    @ApiOperation(value = "获取角色的权限")
    public ResultVo queryPermissionsByRoleId(@ApiParam(value = "角色id", required = true) Long id) {
        Set<SysPermission> permissionsOfRole = sysRoleService.findPermissionsByRoleId(id);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        HashSet<SysPermission> list = new HashSet<>();
        for (SysRole sysRole : sysRoles) {
            list.addAll(sysRoleService.findPermissionsByRoleId(sysRole.getId()));
        }
//        List<SysPermission> list = sysPermissionService.list();
        list.stream().distinct().forEach(i -> {
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
                        new QueryWrapper<SysRole>().like("name", condition).orderByDesc("createTime"));


        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;
        Set<SysRole> roles = loginAppUser.getSysRoles();
        ArrayList<Long> ids = new ArrayList<>();
        for (SysRole role : roles) {
            ids.add(role.getId());
        }
        //判断当前用户有无超级管理员角色，如果有  展示所有角色，如果没有 则展示当前用户已有角色以及已有角色的下级角色
        boolean isSuperAdmin = judgeRoleType(roles, SysConstants.SUPER_ADMIN_ROLE_TYPE);
        if (isSuperAdmin) {
            return PageResult.builder().content(roleIPage.getRecords()).
                    pageNum(roleIPage.getCurrent()).
                    pageSize(roleIPage.getSize()).
                    totalPages(roleIPage.getPages()).
                    totalSize(roleIPage.getTotal()).build();
        }
        //判断当前用户有无普通管理员角色
        boolean isCommonAdmin = judgeRoleType(roles, SysConstants.COMMON_ADMIN_ROLE_TYPE);
        if (isCommonAdmin) {
            List<SysRole> commonUserRoles = sysRoleService.list(new QueryWrapper<SysRole>().eq("roleType", SysConstants.COMMON_USER_ROLE_TYPE));
            commonUserRoles.forEach(s -> ids.add(s.getId()));
            IPage<SysRole> roleIPage2 = sysRoleService.
                    page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                            new QueryWrapper<SysRole>().like("name", condition)
                                    .in("roleType", SysConstants.COMMON_USER_ROLE_TYPE, SysConstants.COMMON_ADMIN_ROLE_TYPE)
                                    .and(i -> i.in("id", ids))
                                    .orderByDesc("createTime"));
            return PageResult.builder().content(roleIPage2.getRecords()).
                    pageNum(roleIPage2.getCurrent()).
                    pageSize(roleIPage2.getSize()).
                    totalPages(roleIPage2.getPages()).
                    totalSize(roleIPage2.getTotal()).build();
        }
        return PageResult.builder().content(null).
                pageNum(pageIndex).
                pageSize(pageSize).
                totalPages(0).
                totalSize(0).build();
    }

    /**
     * 判断当前用户拥有的角色 是什么类型
     *
     * @param roles    当前用户拥有的角色
     * @param roleType 角色类型
     * @return
     */
    private boolean judgeRoleType(Set<SysRole> roles, String roleType) {
        QueryWrapper<SysRole> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("roleType", roleType);
        List<SysRole> list = sysRoleService.list(roleWrapper);
        for (SysRole sysRole : list) {
            if (roles.contains(sysRole)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return 总个数和结果
     * 查询所有的角色类型
     * <p>
     * localhost:8080/api-user/allRoleType
     */
    //@PreAuthorize("hasAuthority('back:role:query')")
    @GetMapping(value = "/allRoleType")
    @ApiOperation(value = "获取全部角色类型")
    public ResultVo selectAllRoleType() {
        ResultVo resultVo = ResultVo.builder().code(200).msg("操作成功！").build();
        //获取登录用户的角色类型
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
        Map<String, String> map = new HashMap<String, String>();
        System.out.println("list.get(0)" + list.get(0));
        if (list.get(0).equals("1")) {
            map.put("1", "超级管理员");
            map.put("2", "普通管理员");
            map.put("3", "普通用户");
        }
        if (list.get(0).equals("2")) {
            map.put("2", "普通管理员");
            map.put("3", "普通用户");
        }
        if (list.get(0).equals("3")) {
            map.put("3", "普通用户");
        }
        resultVo.setData(map);
        return resultVo;
    }

}
