package com.gwm.one.hr.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gwm.one.model.hr.user.LoginAppUser;
import com.gwm.one.model.hr.user.SysRole;
import com.gwm.one.model.hr.user.SysUser;
import com.gwm.one.model.hr.user.constants.SysUserResponse;

import java.util.Set;

public interface SysUserService extends IService<SysUser> {

    void addAppUser(SysUser appUser);

    void addUser(SysUser appUser);

    void updateAppUser(SysUser appUser);

    void updateUser(SysUser appUser);

    LoginAppUser findByUsername(String username);

    SysUser findByPhone(String phone);

    SysUser findById(Long id);

    void setRoleToUser(Long id, Set<Long> roleIds);

    void updatePassword(Long id, String oldPassword, String newPassword);
    void updatePassword2(SysUser user, String oldPassword, String newPassword);

    //Page<SysUser> findUsers(Map<String, Object> params);

    Set<SysRole> findRolesByUserId(Long userId);

    void bindingPhone(Long userId, String phone);

    void deleteUser(SysUser user);

    SysUserResponse getUsers(String personnelID);

    IPage<SysUser> getPage(SysUser user, int pageIndex, int pageSize);
}
