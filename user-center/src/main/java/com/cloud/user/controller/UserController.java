package com.cloud.user.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.VerifyCodeUtils;
import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysGroup;
import com.cloud.model.user.SysRole;
import com.cloud.model.user.SysUser;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.SysRoleService;
import com.cloud.user.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@Api(value = "用户操作", tags = {"userController"})
@SessionAttributes("captchaCode")
public class UserController {

    @Autowired
    private SysUserService appUserService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        loginAppUser.setPassword(null);
        return loginAppUser;
    }

    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        return appUserService.findByUsername(username);
    }

    @GetMapping(value = "/phone-anon/internal", params = "phone")
    public SysUser findByPhone(String phone) {
        return appUserService.findByPhone(phone);
    }

    /**
     * 用户查询
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users")
    public Page<SysUser> findUsers(@RequestParam Map<String, Object> params) {
        return appUserService.findUsers(params);
    }

    @PreAuthorize("hasAuthority('back:user:query')")
    @GetMapping("/users/{id}")
    public SysUser findUserById(@PathVariable Long id) {
        return appUserService.findById(id);
    }

    /**
     * 添加用户,根据用户名注册
     *
     * @param appUser
     */
    @PostMapping("/users-anon/register")
    public SysUser register(@RequestBody SysUser appUser) {
        // 用户名等信息的判断逻辑挪到service了
        appUserService.addAppUser(appUser);

        return appUser;
    }

    /**
     * 修改自己的个人信息
     * register
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_ME)
    @PutMapping("/users/me")
    public SysUser updateMe(@RequestBody SysUser appUser) {
        SysUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());

        appUserService.updateAppUser(appUser);

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
    public ResultVo modifyMineInfo(@RequestBody SysUser appUser) {
        SysUser user = AppUserUtil.getLoginAppUser();
        appUser.setId(user.getId());

        appUserService.updateAppUser(appUser);
        user.setNickname(appUser.getNickname());
        user.setHeadImgUrl(appUser.getHeadImgUrl());
        user.setUsername(appUser.getUsername());
        user.setSex(appUser.getSex());
        user.setPhone(appUser.getPhone());
        return ResultVo.builder().code(200).msg("操作成功!").data(user).build();
    }

    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    public void updatePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }

        SysUser user = AppUserUtil.getLoginAppUser();
        appUserService.updatePassword(user.getId(), oldPassword, newPassword);
    }

    /**
     * 修改密码(element ui)
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @LogAnnotation(module = LogModule.UPDATE_PASSWORD)
    @PostMapping(value = "/users/modifyPassword")
    public ResultVo modifyPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        try {
            SysUser user = AppUserUtil.getLoginAppUser();
            appUserService.updatePassword(user.getId(), oldPassword, newPassword);
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
    public void resetPassword(@PathVariable Long id, String newPassword) {
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
    public void updateAppUser(@RequestBody SysUser appUser) {
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
    public void setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     *
     * @param id 用户id
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @GetMapping("/users/{id}/roles")
    public Set<SysRole> findRolesByUserId(@PathVariable Long id) {
        return appUserService.findRolesByUserId(id);
    }

    @Autowired
    private SmsClient smsClient;

    /**
     * 绑定手机号
     *
     * @param phone
     * @param key
     * @param code
     */
    @PostMapping(value = "/users/binding-phone")
    public void bindingPhone(String phone, String key, String code) {
        if (StringUtils.isBlank(phone)) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("key不能为空");
        }

        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code不能为空");
        }

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        log.info("绑定手机号，key:{},code:{},username:{}", key, code, loginAppUser.getUsername());

        String value = smsClient.matcheCodeAndGetPhone(key, code, false, 30);
        if (value == null) {
            throw new IllegalArgumentException("验证码错误");
        }

        if (phone.equals(value)) {
            appUserService.bindingPhone(loginAppUser.getId(), phone);
        } else {
            throw new IllegalArgumentException("手机号不一致");
        }
    }

    /**
     * 绑定手机号(element ui)
     *
     * @param phone
     * @param key
     * @param code
     */
    @PostMapping(value = "/users/bindPhone")
    public ResultVo bindPhone(String phone, String key, String code) {

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
                return ResultVo.builder().code(50003).data(null).msg(e.getMessage()).build();
            }
        } else {
            return ResultVo.builder().code(50003).data(null).msg("手机号不一致!").build();
        }
    }

    /**
     * 验证码生成
     */
    @PostMapping("/users/captcha")
    public void captchaInit(HttpServletResponse response, Model model) {
        // 生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        log.info("验证码:{}", code);
        // 存入model
        model.addAttribute("captchaCode", code);
        // 设置响应格式
        response.setContentType("image/png");
        // 输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            // 设置宽和高
            int w = 200, h = 80;
            // 将图片输出给浏览器
            VerifyCodeUtils.outputImage(w, h, os, code);
        } catch (IOException e) {
            log.error("生成验证码出现异常!", e);
            throw new IllegalArgumentException("验证码生成出现异常！");
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * @param code     前台传值
     * @param trueCode session中的值
     *                 判断验证码
     */
    @GetMapping("/users/checkCaptcha/{code}")
    public void checkCode(@PathVariable String code, @ModelAttribute("captchaCode") String trueCode) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("请输入验证码！");
        }
        log.info("session中的,code:{}", trueCode);
        log.info("输入的,code:{}", code);
        if (!code.equalsIgnoreCase(trueCode)) {
            throw new IllegalArgumentException("输入的验证码错误！");
        }
        log.info("验证码正确");
    }


    /**
     * 用户查询
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:user:query')")
    @PostMapping("/users/findPages")
    public PageResult findUsersByPages(@RequestBody Map<String, Object> params) {
        // 取值判空
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String username = params.get("username").toString();
        String nickname = params.get("nickname").toString();
        String sex = null;
        String enabled = null;
        if (params.get("sex") != "") {
            sex = params.get("sex").toString();
        }
        if (params.get("enabled") != "") {
            enabled = params.get("enabled").toString();
        }
        IPage<SysUser> userPage = getPage(username, nickname, sex, enabled, pageIndex, pageSize);
        List<SysUser> sysUsers = userPage.getRecords();
        SysGroup sysGroup = SysGroup.builder().build();
        //  查找并设置每个用户所在的分组
        sysUsers.forEach(i -> {
            SysGroup group = sysGroup.selectOne(new QueryWrapper<SysGroup>().lambda()
                    .eq(SysGroup::getId, i.getGroupId()));
            i.setGroup(group);
        });
        // 封装结果
        return PageResult.builder().content(sysUsers).
                pageNum(userPage.getCurrent()).
                pageSize(userPage.getSize()).
                totalPages(userPage.getPages()).
                totalSize(userPage.getTotal()).build();
    }

    // 获取分页的结果
    public IPage<SysUser> getPage(String username, String nickname, String sex, String enabled, Long
            pageIndex, Long pageSize) {
        IPage<SysUser> userPage = null;
        if (sex == null && enabled != null) {
            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                    new QueryWrapper<SysUser>().lambda()
                            .eq(SysUser::getEnabled, enabled)
                            .like(SysUser::getUsername, username)
                            .like(SysUser::getNickname, nickname));
        } else if (enabled == null && sex != null) {
            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                    new QueryWrapper<SysUser>().lambda()
                            .eq(SysUser::getSex, sex)
                            .like(SysUser::getUsername, username)
                            .like(SysUser::getNickname, nickname));
        } else if (sex == null && enabled == null) {
            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                    new QueryWrapper<SysUser>().lambda()
                            .like(SysUser::getUsername, username)
                            .like(SysUser::getNickname, nickname));
        } else {
            userPage = appUserService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),
                    new QueryWrapper<SysUser>().lambda()
                            .eq(SysUser::getSex, sex)
                            .eq(SysUser::getEnabled, enabled)
                            .like(SysUser::getUsername, username)
                            .like(SysUser::getNickname, nickname));
        }
        return userPage;
    }

    /**
     * 管理后台修改用户
     *
     * @param appUser
     */
    @LogAnnotation(module = LogModule.UPDATE_USER)
    @PreAuthorize("hasAuthority('back:user:update')")
    @PutMapping("/editUser")
    public ResultVo updateUser(@RequestBody SysUser appUser) {
        try {
            // 设置该用户所在的组织
            Integer groupId = appUser.getGroupId();
            if (groupId != null) {
                SysGroup group = SysGroup.builder().id(groupId).build();
                if (group.selectById() != null) {
                    appUser.setGroupId(group.getId());
                } else {
                    return new ResultVo(500, "请选择有效组织", null);
                }
            }
            appUserService.updateUser(appUser);
            log.info("修改成功,id:{}", appUser.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("修改出现异常", e);
            return new ResultVo(500, ResponseStatus.RESPONSE_OPERATION_ERROR.message, null);
        }
    }

    /**
     * 管理后台给用户分配角色
     *
     * @param map 用户id和角色id集合
     */
    @LogAnnotation(module = LogModule.SET_ROLE)
    @PreAuthorize("hasAuthority('back:user:role:set')")
    @PostMapping("/users/setRoles")
    public ResultVo setRoleForUser(@RequestBody Map map) {
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
    public ResultVo saveUser(@RequestBody SysUser appUser) {
        try {
            // 设置该用户所在的组织
            Integer groupId = appUser.getGroupId();
            if (groupId != null) {
                SysGroup group = SysGroup.builder().id(groupId).build();
                if (group.selectById() != null) {
                    appUser.setGroupId(group.getId());
                } else {
                    return new ResultVo(500, "请选择有效组织", null);
                }
            }

            appUser.setPassword("123.com");
            appUserService.addUser(appUser);
            log.info("添加成功,id:{}", appUser.getId());
            return new ResultVo(200, ResponseStatus.RESPONSE_SUCCESS.message, null);
        } catch (Exception e) {
            log.error("添加失败", e);
            return new ResultVo(500, e.getMessage(), null);
        }

    }

    /**
     * 获取用户的角色
     *
     * @param id 用户id
     */
    @PreAuthorize("hasAnyAuthority('back:user:role:set','user:role:byuid')")
    @PostMapping("/queryRolesByUserId")
    public ResultVo findRolesById(Long id) {
        Set<SysRole> rolesByUserId = appUserService.findRolesByUserId(id);
        List<SysRole> list = sysRoleService.list();
        list.forEach(i -> {
            if (rolesByUserId.contains(i)) {
                i.setChecked(true);
            }
        });
        return ResultVo.builder().code(200).msg("成功!").data(list).build();
    }

    /**
     * 管理后台，给用户重置密码
     *
     * @param id 用户id
     */
    @LogAnnotation(module = LogModule.RESET_PASSWORD)
    @PreAuthorize("hasAuthority('back:user:password')")
    @PostMapping(value = "/users/{id}/reSetPassword")
    public ResultVo resetPasswordBackend(@PathVariable Long id) {
        try {
            if (null == id) {
                return new ResultVo(500, "id为空", null);
            }
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setPassword("123.com");
            appUserService.updateUser(sysUser);
            log.info("设置新密码成功,userId:{}", id);
            return new ResultVo(200, "操作成功", null);
        } catch (Exception e) {
            log.error("设置新密码失败", e);
            return new ResultVo(500, e.getMessage(), null);
        }
    }


}
