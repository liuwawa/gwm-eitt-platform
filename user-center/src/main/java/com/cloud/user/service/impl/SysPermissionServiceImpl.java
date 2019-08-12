package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.exception.ResultException;
import com.cloud.model.user.SysPermission;
import com.cloud.user.dao.RolePermissionDao;
import com.cloud.user.dao.SysPermissionDao;
import com.cloud.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
        return rolePermissionDao.findPermissionsByRoleIds(roleIds);
    }

    @Transactional
    @Override
    public void addPermissionToRole(Long roleId, Long permissionId) {
        rolePermissionDao.saveRolePermission(roleId, permissionId);
        log.info("给角色添加权限：{}", permissionId + "->" + roleId);
    }


    @Transactional
    @Override
    public void update(SysPermission sysPermission) {
        sysPermission.setUpdateTime(new Date()); // 设置修改时间
        List<SysPermission> permissions = sysPermissionDao.selectList(new QueryWrapper<SysPermission>().lambda()
                .eq(SysPermission::getName, sysPermission.getName())); // 根据名字搜索出所有
        List<SysPermission> permissions1 = permissions.stream().filter(permission -> !(permission.getId().equals(sysPermission.getId())))
                .collect(Collectors.toList()); // 过滤掉传来的对象
        if (null != permissions1 && permissions1.size() != 0) {
            throw new ResultException(10070, "已经存在权限名，修改失败！"); // 如果过滤之后还有对象，则修改失败
        }
        List<SysPermission> sysPermissions = sysPermissionDao.selectList(new QueryWrapper<SysPermission>().lambda()
                .eq(SysPermission::getPermission, sysPermission.getPermission())); // 根据标识搜索所有
        List<SysPermission> sysPermissions1 = sysPermissions.stream().filter(permission -> !(permission.getId().equals(sysPermission.getId())))
                .collect(Collectors.toList()); // // 过滤掉传来的对象
        if (null != sysPermissions1 && sysPermissions1.size() != 0) {
            throw new ResultException(10080, "已经存在权限标识:" + sysPermission.getPermission()); // 如果过滤之后还有对象，则修改失败
        }
        sysPermissionDao.update(sysPermission); // 修改
        log.info("修改权限：{}", sysPermission);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        SysPermission permission = sysPermissionDao.findById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限标识不存在");
        }

        sysPermissionDao.delete(id);
        rolePermissionDao.deleteRolePermission(null, id);
        log.info("删除权限标识：{}", permission);
    }

}
