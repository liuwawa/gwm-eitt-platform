package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.exception.ResultException;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.user.*;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.model.user.constants.SysUserResponse;
import com.cloud.model.user.constants.UserType;
import com.cloud.user.dao.SysUserDao;
import com.cloud.user.dao.UserCredentialsDao;
import com.cloud.user.dao.UserRoleDao;
import com.cloud.user.service.SysPermissionService;
import com.cloud.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserDao appUserDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserCredentialsDao userCredentialsDao;

    @Transactional
    @Override
    public void addAppUser(SysUser appUser) {
        String username = appUser.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (PhoneUtil.checkPhone(username)) {// 防止用手机号直接当用户名，手机号要发短信验证
            throw new IllegalArgumentException("用户名要包含英文字符");
        }

        if (username.contains("@")) {// 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
            throw new IllegalArgumentException("用户名不能包含@");
        }

        if (username.contains("|")) {
            throw new IllegalArgumentException("用户名不能包含|字符");
        }

        if (StringUtils.isBlank(appUser.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (StringUtils.isBlank(appUser.getNickname())) {
            appUser.setNickname(username);
        }

        if (StringUtils.isBlank(appUser.getType())) {
            appUser.setType(UserType.APP.name());
        }

        UserCredential userCredential = userCredentialsDao.findByUsername(appUser.getUsername());
        if (userCredential != null) {
            throw new IllegalArgumentException("用户名已存在");
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword())); // 加密密码
        appUser.setEnabled(Boolean.TRUE);
        appUser.setCreateTime(new Date());
        appUser.setUpdateTime(appUser.getCreateTime());

        appUserDao.save(appUser);
        userCredentialsDao
                .save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
        log.info("添加用户：{}", appUser);
    }

    @Transactional
    @Override
    public void updateAppUser(SysUser appUser) {
        // appUser.setUpdateTime(new Date());

        appUserDao.updateById(appUser);
        QueryWrapper<UserCredential> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("userId", appUser.getId())
                .and(
                        i -> i.eq("type", CredentialType.PHONE.name())
                );
        userCredentialsDao.delete(deleteWrapper);
//        QueryWrapper<UserCredential> wrapper = new QueryWrapper<>();
//        wrapper.eq("userId",appUser.getId());
        userCredentialsDao.save(new UserCredential(appUser.getPhone(), CredentialType.PHONE.name(), appUser.getId()));
        log.info("修改用户：{}", appUser);
    }

    @Transactional
    @Override
    public LoginAppUser findByUsername(String username) {
        SysUser appUser = userCredentialsDao.findUserByUsername(username);
        if (appUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(appUser, loginAppUser);

            Set<SysRole> sysRoles = userRoleDao.findRolesByUserId(appUser.getId());
            loginAppUser.setSysRoles(sysRoles);// 设置角色

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(SysRole::getId).collect(Collectors.toSet());
                Set<SysPermission> sysPermissions = sysPermissionService.findByRoleIds(roleIds);
                if (!CollectionUtils.isEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(SysPermission::getPermission)
                            .collect(Collectors.toSet());

                    loginAppUser.setPermissions(permissions);// 设置权限集合
                }

            }

            return loginAppUser;
        }

        return null;
    }

    @Override
    public SysUser findByPhone(String phone) {
        return appUserDao.findByPhone(phone);
    }

    @Override
    public SysUser findById(Long id) {
        return appUserDao.findById(id);
    }

    /**
     * 给用户设置角色<br>
     * 这里采用先删除老角色，再插入新角色
     */
    @Transactional
    @Override
    public void setRoleToUser(Long id, Set<Long> roleIds) {
        SysUser appUser = appUserDao.findById(id);
        if (appUser == null) {
            throw new IllegalArgumentException(ResponseStatus.RESPONSE_ACCOUNT_ERROR.message);
        }

        userRoleDao.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                userRoleDao.saveUserRoles(id, roleId);
            });
        }

        log.info("修改用户：{}的角色，{}", appUser.getUsername(), roleIds);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) throws IllegalArgumentException {
        SysUser appUser = appUserDao.findById(id);
        if (StringUtils.isNoneBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) { // 旧密码校验
                throw new IllegalArgumentException("旧密码错误");
            }
        }

        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(passwordEncoder.encode(newPassword)); // 加密密码

        updateAppUser(user);
        log.info("修改密码：{}", user);
    }

    /**
     * 修改密码(element ui)
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    @Override
    public void updatePassword2(SysUser user, String oldPassword, String newPassword) throws IllegalArgumentException {
        SysUser appUser = appUserDao.findById(user.getId());
        if (StringUtils.isNoneBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) { // 旧密码校验
                throw new IllegalArgumentException("旧密码错误");
            }
        }

        appUser.setPassword(passwordEncoder.encode(newPassword)); // 加密密码

        appUserDao.updateById(appUser);
        log.info("修改密码：{}", user);
    }

    @Override
    public Page<SysUser> findUsers(Map<String, Object> params) {
        int total = appUserDao.count(params);
        List<SysUser> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = appUserDao.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Set<SysRole> findRolesByUserId(Long userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

    /**
     * 绑定手机号
     */
    @Transactional
    @Override
    public void bindingPhone(Long userId, String phone) throws IllegalArgumentException {
        UserCredential userCredential = userCredentialsDao.findByUsername(phone);
        if (userCredential != null) {
            throw new IllegalArgumentException("手机号已被绑定");
        }

        SysUser appUser = appUserDao.findById(userId);
        appUser.setPhone(phone);

        appUserDao.updateById(appUser);
        log.info("绑定手机号成功,username:{}，phone:{}", appUser.getUsername(), phone);

        // 绑定成功后，将手机号存到用户凭证表，后续可通过手机号+密码或者手机号+短信验证码登陆
        userCredentialsDao.save(new UserCredential(phone, CredentialType.PHONE.name(), userId));
    }


    @Transactional
    @Override
    public void addUser(SysUser appUser) {
        String username = appUser.getUsername();
        if (isContainsChinese(username)) {
            throw new ResultException(500, "用户名不能含有中文字符");
        }
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        if (PhoneUtil.checkPhone(username)) {// 防止用手机号直接当用户名，手机号要发短信验证
            throw new IllegalArgumentException("用户名要包含英文字符");
        }

        if (username.contains("@")) {// 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
            throw new IllegalArgumentException("用户名不能包含@");
        }

        if (username.contains("|")) {
            throw new IllegalArgumentException("用户名不能包含|字符");
        }

        if (StringUtils.isBlank(appUser.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (StringUtils.isBlank(appUser.getNickname())) {
            appUser.setNickname(username);
        }

        //默认添加后台用户
        if (StringUtils.isBlank(appUser.getType())) {
            appUser.setType(UserType.BACKEND.name());
        }

        UserCredential userCredential = userCredentialsDao.findByUsername(appUser.getUsername());
        if (userCredential != null) {
            throw new IllegalArgumentException("用户名已存在");
        }

        String md5DigestAsHex = DigestUtils.md5DigestAsHex(appUser.getPassword().getBytes());

        appUser.setPassword(passwordEncoder.encode(md5DigestAsHex)); // 加密密码
        appUser.setEnabled(Boolean.TRUE);
        appUser.setCreateTime(new Date());
        appUser.setUpdateTime(appUser.getCreateTime());

        // 自动生成下一个工号
        String personnelNO = appUserDao.selectMaxPersonnelNO();
        if (personnelNO != null) {
            Integer personelno = Integer.valueOf(personnelNO.substring(2, 10));
            personelno = personelno + 1;
            String zeroForNum = addZeroForNum(personelno.toString(), 8);
            appUser.setPersonnelID("GW" + zeroForNum);
        } else {
            appUser.setPersonnelID("GW00000001");
        }

        appUserDao.insert(appUser);
        int userId = appUser.getId().intValue();
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().userId(userId).build();
        // 先进行删除
        sysUserGrouping.delete(new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, userId));
        // 如果传来的不是空 在做add操作
        if (appUser.getGroupingIds().size() != 0 && appUser.getGroupingIds() != null) {
            // 添加
            for (Integer groupingId : appUser.getGroupingIds()) {
                sysUserGrouping.setGroupingId(groupingId);
                sysUserGrouping.insert();
            }
        }

        userCredentialsDao
                .save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
        log.info("添加用户：{}", appUser);
    }

    // 计算工号
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);// 左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }

    @Transactional
    @Override
    public void updateUser(SysUser appUser) {
        if (isContainsChinese(appUser.getUsername())) {
            throw new ResultException(500, "用户名不能含有中文字符");
        }
        appUserDao.updateById(appUser);
        QueryWrapper<UserCredential> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("userId", appUser.getId())
                .and(
                        i -> i.eq("type", CredentialType.USERNAME.name())
                );
        userCredentialsDao.delete(deleteWrapper);

        int userId = appUser.getId().intValue();
        SysUserGrouping sysUserGrouping = SysUserGrouping.builder().userId(userId).build();
        // 先进行删除
        sysUserGrouping.delete(new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, userId));
        // 如果传来的不是空 在做add操作
        if (appUser.getGroupingIds().size() != 0 && appUser.getGroupingIds() != null) {
            // 添加
            for (Integer groupingId : appUser.getGroupingIds()) {
                sysUserGrouping.setGroupingId(groupingId);
                sysUserGrouping.insert();
            }
        }

        userCredentialsDao.save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
        log.info("修改用户：{}", appUser);
    }

    //  验证是否含有中文
    public static boolean isContainsChinese(String str) {
        Pattern pat = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    @Transactional
    @Override
    public void deleteUser(SysUser appUser) {

        appUserDao.updateById(appUser);

        log.info("用户id：{}", appUser.getId());
    }

    @Override
    public SysUserResponse getUsers(String personnelID) {
        SysUser sysUser = appUserDao.selectOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getPersonnelID, personnelID));
        if (sysUser == null) {
            throw new ResultException(500, "查无此人");
        }
        SysGroup sysGroup = SysGroup.builder().id(sysUser.getGroupId()).build();
        SysGroup group = sysGroup.selectById();
        SysUserResponse userResponse = SysUserResponse.builder()
                .duties(sysUser.getDuties())
                .personnelID(personnelID)
                .nickname(sysUser.getNickname()).build();
        if (group == null) {
            userResponse.setGroupName(null);
        } else {
            userResponse.setGroupName(group.getLabel());
        }
        return userResponse;

    }
}
