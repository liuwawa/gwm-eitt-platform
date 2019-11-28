package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.hr.user.SysPermission;

import java.util.Set;

public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 根绝角色ids获取权限集合
     *
     * @param roleIds
     * @return
     */
    Set<SysPermission> findByRoleIds(Set<Long> roleIds);


    void update(SysPermission sysPermission);

    void delete(Long id);

    void addPermissionToRole(Long roleId, Long permissionId);

}
