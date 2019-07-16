package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.common.Page;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.constants.SysUserResponse;

import java.util.List;
import java.util.Map;
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

    Page<SysUser> findUsers(Map<String, Object> params);

    Set<SysRole> findRolesByUserId(Long userId);

    void bindingPhone(Long userId, String phone);

    void deleteUser(SysUser user);

    List<SysUserResponse> getUsers();
}
