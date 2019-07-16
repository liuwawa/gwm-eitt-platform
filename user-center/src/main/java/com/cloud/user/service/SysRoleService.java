package com.cloud.user.service;

import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysRole;
import org.springframework.data.domain.PageRequest;

public interface SysRoleService extends IService<SysRole> {

//	void saveSysRole(SysRole sysRole);

	void update(SysRole sysRole);

	void deleteRole(Long id);

	void setPermissionToRole(Long id, Set<Long> permissionIds);

	SysRole findById(Long id);

	Page<SysRole> findRoles(Map<String, Object> params);

//	PageResult findPage(Map params);

	Set<SysPermission> findPermissionsByRoleId(Long roleId);
}
