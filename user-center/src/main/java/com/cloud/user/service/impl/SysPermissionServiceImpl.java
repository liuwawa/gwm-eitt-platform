package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.model.user.SysPermission;
import com.cloud.user.dao.RolePermissionDao;
import com.cloud.user.dao.SysPermissionDao;
import com.cloud.user.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

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
    public void update(SysPermission sysPermission) {
        sysPermission.setUpdateTime(new Date());
        sysPermissionDao.update(sysPermission);
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
