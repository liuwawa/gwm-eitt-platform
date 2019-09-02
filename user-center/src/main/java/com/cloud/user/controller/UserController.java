package com.cloud.user.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.constants.SysConstants;
import com.cloud.common.plugins.ApiJsonObject;
import com.cloud.common.plugins.ApiJsonProperty;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.*;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.model.user.constants.SysUserResponse;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.SysRoleService;
import com.cloud.user.service.SysUserService;
import com.cloud.user.service.UserCredentialsService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(value = "用户操作", tags = {"用户操作接口 UserController"})
public class UserController {

    @Autowired
    private SysUserService appUserService;

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserCredentialsService userCredentialsService;


    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    @ApiOperation(value = "获取登录用户信息")
    public LoginAppUser getLoginAppUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        loginAppUser.setPassword(null);
        return loginAppUser;
    }

    @GetMapping(value = "/users-anon/internal", params = "username")
    @ApiOperation(value = "根据用户名查找用户信息")
    public LoginAppUser findByUsername(@ApiParam(value = "用户名", required = true) String username) {
        return appUserService.findByUsername(username);
    }

    @GetMapping(value = "/phone-anon/internal", params = "phone")
    @ApiOperation(value = "根据手机号查找用户信息")
    public SysUser findByPhone(@ApiParam(value = "用户手机号", required = true) String phone) {
        return appUserService.findByPhone(phone);
    }

    /**
     * 用户查询
     *
     * @param
     */
//    @PreAuthorize("hasAuthority('back:user:query')")
//    @GetMapping("/users")
//    @ApiOperation(value = "分页查找用户")
//    public Page<SysUser> findUsers(@RequestParam Map<String, Object> params) {
//        return appUserService.findUsers(params);
//    }
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    @ApiOperation(value = "根据id查找")
    public SysUser findUserById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    /**
     * 添加用户,根据用户名注册
     *
     * @param appUser
     */
    @PostMapping("/users-anon/register")
    @ApiOperation(value = "添加用户")
    public SysUser register(@RequestBody SysUser appUser) {
        // 用户名等信息的判断逻辑挪到service了
        appUserService.addAppUser(appUser);

        return appUser;
    }


    /**
     * 修改自己的个人信息 (element ui)
     * register
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_ME)
    @PostMapping("/users/modifyMineInfo")
    @ApiOperation(value = "修改用户信息")
    public ResultVo modifyMineInfo(@ApiParam(value = "SysUser对象") @RequestBody SysUser appUser) {
        SysUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());
        appUser.setUpdateTime(new Date());
        appUserService.updateAppUser(appUser);
        user.setNickname(appUser.getNickname());
        user.setHeadImgUrl(appUser.getHeadImgUrl());
        user.setUsername(appUser.getUsername());
        user.setSex(appUser.getSex());
        user.setPhone(appUser.getPhone());
        return ResultVo.builder().code(200).msg("操作成功!").data(user).build();
    }


    /**
     * 修改密码(element ui)
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PostMapping(value = "/users/modifyPassword")
    @ApiOperation(value = "修改密码")
    public ResultVo modifyPassword(@ApiParam(value = "旧密码", required = true)
                                   @RequestParam("oldPassword") String oldPassword,
                                   @ApiParam(value = "新密码", required = true)
                                   @RequestParam("newPassword") String newPassword) {
        try {
            SysUser user = AppUserUtil.getLoginAppUser();
            appUserService.updatePassword2(user, oldPassword, newPassword);
            return ResultVo.builder().code(200).msg("操作成功!").data(null).build();
        } catch (Exception e) {
            log.info(e + "");
            if ("旧密码错误".equals(e.getMessage())) {
                return ResultVo.builder().code(5000).msg(e.getMessage()).data(null).build();
            }
            return ResultVo.builder().code(5000).msg("请联系管理员!").data(null).build();

        }


    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id          用户id
     * @param newPassword 新密码
     */
    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @PutMapping(value = "/users/{id}/password", params = {"newPassword"})
    @ApiOperation(value = "重置密码")
    public void resetPassword(@ApiParam(value = "用户id", required = true)
                              @PathVariable Long id, @ApiParam(value = "新密码",
            required = true) String newPassword) {
        appUserService.updatePassword(id, null, newPassword);
    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/users")
    @ApiOperation(value = "修改用户")
    public void updateAppUser(@ApiParam(value = "用户", required = true) @RequestBody SysUser appUser) {
        appUserService.updateAppUser(appUser);
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param id      用户id
     * @param roleIds 角色ids
     */
    @LogAnnotation(module = LogModule.SET_ROLE)
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/{id}/roles")
    @ApiOperation(value = "用户分配角色")
    public void setRoleToUser(@ApiParam(value = "用户id", required = true) @PathVariable Long id, @RequestBody Set<Long> roleIds) {
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     *
     * @param id 用户id
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set')")
    @GetMapping("/users/{id}/roles")
    @ApiOperation(value = "根据用户id查询用户角色")
    public Set<SysRole> findRolesByUserId(@PathVariable Long id) {
        return appUserService.findRolesByUserId(id);
    }

    @Autowired
    private SmsClient smsClient;

    /**
     * 查询手机号是否存在
     *
     * @param phone
     * @return
     */
    @PostMapping("/users/queryPhone")
    @ApiOperation(value = "查询手机号是否存在")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo queryPhone(@ApiParam(value = "手机号", required = true) String phone) {
        if (appUserService.findByPhone(phone) != null) {
            return ResultVo.builder().code(50003).data(null).msg("手机号已被绑定!").build();
        }
        return null;
    }

    /**
     * 绑定手机号(element ui)
     *
     * @param phone
     * @param key
     * @param code
     */
    @PostMapping(value = "/users/bindPhone")
    @ApiOperation(value = "绑定手机号")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo bindPhone(@ApiParam(value = "手机号", required = true) String phone,
                              @ApiParam(value = "redis 中的key值，根据key取值去与验证码对比", required = true) String key,
                              @ApiParam(value = "验证码", required = true) String code) {

        if (StringUtils.isBlank(phone)) {
            return ResultVo.builder().code(50001).data(null).msg("手机号不能为空!").build();
        }

        if (StringUtils.isBlank(code)) {
            return ResultVo.builder().code(50002).data(null).msg("验证码不能为空!").build();
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;
        log.info("绑定手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());

        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 10);
        if (value == null) {
            return ResultVo.builder().code(50003).data(null).msg("验证码错误!").build();
        }

        if (appUserService.findByPhone(phone) != null) {
            return ResultVo.builder().code(50003).data(null).msg("手机号已被绑定!").build();
        }

        if (phone.equals(value)) {
            try {
                appUserService.bindingPhone(loginAppUser.getId(), phone);
                return ResultVo.builder().code(200).data(null).msg("绑定成功!").build();
            } catch (Exception e) {
                log.info(e + "");
                return ResultVo.builder().code(50003).data(null).msg("请联系管理员!").build();
            }
        } else {
            return ResultVo.builder().code(50003).data(null).msg("手机号不一致!").build();
        }
    }

    /**
     * 修改手机号 （element ui）
     *
     * @param oldPhone 原手机号
     * @param newPhone 新手机号
     * @param key      验证码key
     * @param code     验证码
     * @return
     */
    @PostMapping(value = "/users/changePhone")
    @ApiOperation(value = "修改手机号")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo changePhone(@ApiParam(value = "原手机号", required = true) String oldPhone,
                                @ApiParam(value = "新手机号", required = true) String newPhone,
                                @ApiParam(value = "redis 中的key值，根据key取值去与验证码对比", required = true) String key,
                                @ApiParam(value = "验证码", required = true) String code) {
        if (StringUtils.isBlank(code)) {
            return ResultVo.builder().code(50002).data(null).msg("验证码不能为空!").build();
        }
        if (StringUtils.isBlank(oldPhone)) {
            return ResultVo.builder().code(50000).data(null).msg("原手机号不能为空!").build();
        }
        if (StringUtils.isBlank(newPhone)) {
            return ResultVo.builder().code(50001).data(null).msg("新手机号不能为空!").build();
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        assert loginAppUser != null;

        if (!loginAppUser.getPhone().equals(oldPhone)) {
            return ResultVo.builder().code(50005).data(null).msg("原手机号错误!").build();
        }
        if (appUserService.findByPhone(newPhone) != null) {
            return ResultVo.builder().code(50003).data(null).msg("新手机号已被绑定!").build();
        }
        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 10);
        if (value == null) {
            return ResultVo.builder().code(50004).data(null).msg("验证码错误!").build();
        }

        log.info("修改手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());
        if (newPhone.equals(value)) {
            try {
                QueryWrapper<SysUser> userWrapper = new QueryWrapper<>();
                userWrapper.eq("id", loginAppUser.getId());
                SysUser sysUser = appUserService.getById(loginAppUser.getId());
                sysUser.setPhone(newPhone);
                appUserService.update(sysUser, userWrapper);
                QueryWrapper<UserCredential> userCredentialsWrapper = new QueryWrapper<>();
                userCredentialsWrapper.eq("type", CredentialType.PHONE.name())
                        .and(i -> i.eq("username", oldPhone))
                        .and(i -> i.eq("userId", loginAppUser.getId()));
                UserCredential userCredential = userCredentialsService.getOne(userCredentialsWrapper);
                userCredential.setUsername(newPhone);
                userCredentialsService.update(userCredential, userCredentialsWrapper);
                return ResultVo.builder().code(200).data(null).msg("手机号修改成功!").build();
            } catch (Exception e) {
                log.info(e + "");
                return ResultVo.builder().code(50006).data(null).msg("请联系管理员!").build();
            }
        } else {
            return ResultVo.builder().code(50007).data(null).msg("手机号不一致!").build();
        }
    }


    /**
     * 用户查询
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @PostMapping("/users/findPages")
    @ApiOperation(value = "分页，多条件查询全部用户",
            notes = "参数：pageNum（必填），pageSize（必填），username，nickname，sex，enabled")
    public PageResult findUsersByPages(
            @ApiJsonObject(name = "分页，多条件查询全部用户", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "username", example = "username", description = "username"),
                    @ApiJsonProperty(key = "nickname", example = "nickname", description = "nickname"),
                    @ApiJsonProperty(key = "personnelNO", example = "0", description = "personnelNO"),
                    @ApiJsonProperty(key = "duties", example = "duties", description = "duties"),
                    @ApiJsonProperty(key = "sex", example = "0", description = "性别 0:女1:男"),
                    @ApiJsonProperty(key = "enabled", example = "false", description = "enabled")
            })
            @RequestBody Map<String, Object> params) {
        SysUser user = new SysUser();
        // 取值判空
        int pageIndex = Integer.parseInt(params.get("pageNum").toString());
        //int pageIndex = Integer.valueOf(params.get("pageNum").toString());
        int pageSize = Integer.parseInt(params.get("pageSize").toString());
        String username = params.get("username").toString();
        String nickname = params.get("nickname").toString();
        String personnelNO = params.get("personnelNO").toString();
        String duties = params.get("duties").toString();

        String sex = null;
        String enabled = null;
        if (!"".equals(params.get("sex"))) {
            sex = params.get("sex").toString();

        }
        if (!"".equals(params.get("enabled"))) {
            enabled = params.get("enabled").toString();
        }
        if (StringUtils.isNoneBlank(username)) {
            user.setUsername(username);
        }
        if (StringUtils.isNoneBlank(nickname)) {
            user.setNickname(nickname);
        }
        if (StringUtils.isNoneBlank(personnelNO)) {
            user.setPersonnelNO(personnelNO);
        }
        if (StringUtils.isNoneBlank(duties)) {
            user.setDuties(duties);
        }
        if (sex != null && !"".equals(sex)) {
            user.setSex(Integer.parseInt(sex));
        }
        if (enabled != null && !"".equals(enabled)) {
            if (enabled.equals("1")) {
                user.setEnabled(true);
            } else {
                user.setEnabled(false);
            }

        }

        IPage<SysUser> userPage = appUserService.getPage(user, pageIndex, pageSize);
        List<SysUser> sysUsers = userPage.getRecords();


        //SysGroup sysGroup = SysGroup.builder().build();
        //initUserGrouping(sysUsers, sysGroup);

        // 封装结果
        return PageResult.builder().content(sysUsers).
                pageNum(userPage.getCurrent()).
                pageSize(userPage.getSize()).
                totalPages(userPage.getPages()).
                totalSize(userPage.getTotal()).build();
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
     * 查找并设置每个用户所在的分组和可以查看的分组
     *
     * @param sysUsers
     * @param sysGroup
     */
    private void initUserGrouping(List<SysUser> sysUsers, SysGroup sysGroup) {
        // 获取当前对象的角色是否含有超级管理员
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        //List<Long> list = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        //登录用户的角色类型
        List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
        //boolean contains = list.contains(SysConstants.ADMIN_ROLE_ID);
        boolean contains = list.contains(SysConstants.SUPER_ADMIN_ROLE_TYPE);
        SysUserGrouping userGrouping = SysUserGrouping.builder().build();
        //根据登录用户ID查询分组信息
        List<SysUserGrouping> userSysGroupings = userGrouping.selectList(new QueryWrapper<SysUserGrouping>().lambda()
                .eq(SysUserGrouping::getUserId, loginAppUser.getId()));
        SysGrouping grouping = SysGrouping.builder().build();
        sysUsers.forEach(i -> {
            //循环查询---根据用户的groupId查询用户的组织信息
            SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                    .eq(SysGroup::getId, i.getGroupId()));
            i.setGroup(group);
            //循环查询---根据用户id查询用户的分组信息
            List<SysUserGrouping> userGroupings = userGrouping.selectList(new QueryWrapper<SysUserGrouping>().lambda()
                    .eq(SysUserGrouping::getUserId, i.getId()));
            List<SysUserGrouping> list1 = new ArrayList<>();
            List<Integer> groupingIds = new ArrayList<>();
            //  没有超级管理员的权限，只找出当前登录用户管理的分组，并返回
            if (!contains) {
                userGroupings.forEach(sysUserGrouping -> {
                    if (userSysGroupings.contains(sysUserGrouping)) {
                        list1.add(sysUserGrouping);
                    }
                });
                groupingIds = list1.stream().map(SysUserGrouping::getGroupingId).collect(Collectors.toList());
            } else {
                // 否则所有一起返回
                groupingIds = userGroupings.stream().map(SysUserGrouping::getGroupingId).collect(Collectors.toList());
            }
            i.setGroupingIds(groupingIds);

            if (groupingIds.size() != 0 && groupingIds != null) {
                List<SysGrouping> sysGroupings = grouping.selectList(new QueryWrapper<SysGrouping>()
                        .lambda().in(SysGrouping::getGroupingId, groupingIds));
                i.setGroupingsList(sysGroupings);
            }

        });
    }

    // 获取分页的结果
//    public IPage<SysUser> getPage(String username, String nickname, String sex, String enabled, String personnelNO, String duties, Long
//            pageIndex, Long pageSize) {
//        // 找出当前对象管理的所有的分组下的组织，并找出这些组织中的用户
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
//        List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
//        boolean contains = list.contains(SysConstants.SUPER_ADMIN_ROLE_TYPE);
//        LambdaQueryWrapper<SysUser> userQueryWrapper = new LambdaQueryWrapper<>();
//        IPage<SysUser> userPage = null;
//        if (!contains) {
//            //不是超级管理员
//            //用户ID
//            Long id = loginAppUser.getId();
//            // 先取出该用户管理的所有分组
//            SysUserGrouping userGrouping = SysUserGrouping.builder().build();
//            //根据用户ID查询分组
//            List<SysUserGrouping> sysUserGroupings = userGrouping.selectList(
//                    new QueryWrapper<SysUserGrouping>().lambda().eq(SysUserGrouping::getUserId, id));
//            List<Integer> groupingIds = sysUserGroupings.stream().map(SysUserGrouping::getGroupingId).collect(Collectors.toList());
//            SysGroupGrouping groupGrouping = SysGroupGrouping.builder().build();
//            //根据分组ID查询组织信息
//            List<SysGroupGrouping> groupGroupings = groupGrouping.selectList(
//                    new QueryWrapper<SysGroupGrouping>().lambda().in(SysGroupGrouping::getGroupingId, groupingIds));
//            List<Integer> groupIds = groupGroupings.stream().map(SysGroupGrouping::getGroupId).collect(Collectors.toList());
//
//            userQueryWrapper = new QueryWrapper<SysUser>().lambda()
//                    .in(SysUser::getGroupId, groupIds)
//                    .like(SysUser::getUsername, username)
//                    .like(SysUser::getDuties, duties)
//                    .like(SysUser::getPersonnelNO, personnelNO)
//                    .like(SysUser::getNickname, nickname).orderByDesc(SysUser::getCreateTime);
//        } else {
//            userQueryWrapper = new QueryWrapper<SysUser>().lambda()
//                    .like(SysUser::getUsername, username)
//                    .like(SysUser::getDuties, duties)
//                    .like(SysUser::getPersonnelNO, personnelNO)
//                    .like(SysUser::getNickname, nickname).orderByDesc(SysUser::getCreateTime);
//        }
//
//        if (null == sex && null != enabled) {
//            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
//                    userQueryWrapper.eq(SysUser::getEnabled, enabled));
//        } else if (null == enabled && null != sex) {
//            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
//                    userQueryWrapper.eq(SysUser::getSex, sex));
//        } else if (null == sex && null == enabled) {
//            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
//                    userQueryWrapper);
//        } else {
//            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
//                    userQueryWrapper.eq(SysUser::getSex, sex).eq(SysUser::getEnabled, enabled));
//        }
//        return userPage;
//    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/editUser")
    @ApiOperation(value = "修改用户")
    public ResultVo updateUser(@RequestBody SysUser appUser) {
        try {
            // 设置该用户所在的组织
            if (setUserGroup(appUser)) {
                return new ResultVo(500, "请选择有效组织", null);
            }

            //修改之前要先判断一下（不能修改本级）
            // 登录用户的角色类型
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            Set<SysRole> sysRoles = loginAppUser.getSysRoles();
            List<String> list = sysRoles.stream().map(SysRole::getRoleType).collect(Collectors.toList());
            //被修改用户的角色类型
            SysRole updateRole = sysRoleService.findByUserId(appUser.getId());
            if (null != updateRole) {
                //如果被修改用户的角色类型==登录用户的角色类型且不是超级管理员，则不能修改
                if (list.get(0).equals(updateRole.getRoleType()) && !list.get(0).equals("1")) {
                    //return ResultVo.builder().msg("没有权限").data(null).code(500).build();
                    return new ResultVo(500, ResponseStatus.RESPONSE_NO_PERMISSION.message, null);
                }
            }


            appUserService.updateUser(appUser);
            log.info("修改成功,id:{}", appUser.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("修改出现异常", e);
            return new ResultVo(500, e.getMessage(), null);
        }
    }

    /**
     * 设置用户所在组织
     *
     * @param appUser
     * @return
     */
    private boolean setUserGroup(@RequestBody SysUser appUser) {
        Integer groupId = appUser.getGroupId();
        if (groupId != null) {
            SysGroup group = SysGroup.builder().id(groupId).build();
            if (group.selectById() != null) {
                appUser.setGroupId(group.getId());
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param map 用户id和角色id集合
     */
    @LogAnnotation(module = LogModule.SET_ROLE)
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/setRoles")
    @ApiOperation(value = "用户设置角色",
            notes = "必填参数： userId,roleIds（数组）")
    public ResultVo setRoleForUser(
            @ApiJsonObject(name = "用户设置角色", value = {
                    @ApiJsonProperty(key = "roleIds", example = "[]", description = "roleIds"),
                    @ApiJsonProperty(key = "userId", example = "0", description = "userId")})
            @RequestBody Map map) {
        try {
            Long id = Long.valueOf(map.get("userId").toString());
            HashSet<Long> roleIds = new HashSet<>(JSONArray.parseArray(map.get("roleIds").toString(), Long.class));
            appUserService.setRoleToUser(id, roleIds);
            log.info("分配角色成功,用户id:{}", id);
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("分配失败", e);
            return new ResultVo(500, e.getMessage(), null);
        }

    }


    /**
     * 添加用户
     *
     * @param appUser
     */
    @PreAuthorize("hasAuthority('back:user:save')")
    @PostMapping("/user/saveUser")
    @ApiOperation(value = "添加用户")
    public ResultVo saveUser(@RequestBody SysUser appUser) {
        try {
            // 设置该用户所在的组织
            if (setUserGroup(appUser)) {
                return new ResultVo(500, "请选择有效组织", null);
            }

            appUser.setPassword("123456");
            appUserService.addUser(appUser);
            log.info("添加成功,id:{}", appUser.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加失败", e);
            return new ResultVo(500, e.getMessage(), null);
        }

    }

    /**
     * 获取用户的角色（element ui）
     *
     * @param id 用户id
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @PostMapping("/queryRolesByUserId")
    @ApiOperation(value = "查看用户角色")
    public ResultVo findRolesById(@ApiParam(value = "用户id", required = true) Long id) {
        // 搜索点击的用户拥有的所有角色对象
        Set<SysRole> rolesByUserId = appUserService.findRolesByUserId(id);

        // 查看当前用户的所有角色对象
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();

        List<Long> roleIds = new ArrayList<>();
        sysRoles.forEach(m -> {
            roleIds.add(m.getId());
        });
        // 查看是否有超级管理员的权限
        if (roleIds.contains(SysConstants.ADMIN_ROLE_ID)) {
            List<SysRole> list = sysRoleService.list();  // 有超级管理员角色则进行全查
            setRoleChecked(list, rolesByUserId);
            return ResultVo.builder().code(200).msg("成功!").data(list).build();
        } else {
            setRoleChecked(sysRoles, rolesByUserId); // 没有，只取当前对象拥有的角色
            return ResultVo.builder().code(200).msg("成功!").data(sysRoles).build();
        }


    }

    // 设置该用户所拥有的角色
    public void setRoleChecked(Collection<SysRole> collection, Set<SysRole> rolesByUserId) {
        collection.forEach(i -> {
            if (rolesByUserId.contains(i)) {
                i.setChecked(true);
            }
        });
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id 用户id
     */
    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @PostMapping(value = "/users/{id}/reSetPassword")
    @ApiOperation(value = "用户重置密码")
    public ResultVo resetPasswordBackend(@PathVariable Long id) {
        try {
            if (null == id) {
                return new ResultVo(500, "id为空", null);
            }
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setPassword("123456");
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
            sysUser.setPassword(passwordEncoder.encode(md5DigestAsHex)); // 加密密码
            appUserService.deleteUser(sysUser);
            log.info("设置新密码成功,userId:{}", id);
            return new ResultVo(200, "操作成功", null);
        } catch (Exception e) {
            log.error("设置新密码失败", e);
            return new ResultVo(500, e.getMessage(), null);
        }
    }

    /**
     * 组织返回用户信息
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping(value = "/users/getUser", params = "personnelID")
    @ApiOperation(value = "根据工号查找员工信息")
    public ResultVo getUsersForGroup(@ApiParam(value = "员工工号", required = true) String personnelID) {
        try {
            SysUserResponse user = appUserService.getUsers(personnelID);
            if (null == user) {
                return new ResultVo(500, "查无此人", null);
            }
            return new ResultVo(200, "操作成功", user);
        } catch (Exception e) {
            log.info("根据工号查找用户出现错误", e);
            return new ResultVo(500, e.getMessage(), null);
        }

    }


    /**
     * 管理后台禁用
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/deleteUser")
    @ApiOperation(value = "逻辑删除用户")
    public ResultVo deleteUser(@RequestBody SysUser appUser) {
        try {
            appUserService.deleteUser(appUser);
            log.info("禁用成功,id:{}", appUser.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("禁用出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
    }
}
