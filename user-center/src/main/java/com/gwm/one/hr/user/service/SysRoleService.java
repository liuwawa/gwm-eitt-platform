package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.common.Page;
import com.gwm.one.model.hr.user.SysPermission;
import com.gwm.one.model.hr.user.SysRole;

import java.util.Map;
import java.util.Set;

public interface SysRoleService extends IService<SysRole> {

//	void saveSysRole(SysRole sysRole);

    void update(SysRole sysRole);

    void deleteRole(Long id);

    void setPermissionToRole(Long id, Set<Long> permissionIds);

    SysRole findById(Long id);

    Page<SysRole> findRoles(Map<String, Object> params);

    Set<SysPermission> findPermissionsByRoleId(Long roleId);


    SysRole findByUserId(Long userId);
}
