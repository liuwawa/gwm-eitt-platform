package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.user.SysPermission;

import java.util.Set;

public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 根绝角色ids获取权限集合
     *
     * @param roleIds
     * @return
     */
    Set<SysPermission> findByRoleIds(Set<Long> roleIds);

//	void saveSysPermission(SysPermission sysPermission);

    void update(SysPermission sysPermission);

    void delete(Long id);

//	Page<SysPermission> findPermissions(Map<String, Object> params);
}
