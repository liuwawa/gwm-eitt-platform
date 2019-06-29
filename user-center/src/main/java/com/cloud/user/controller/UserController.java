package com.cloud.user.controller;


import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.VerifyCodeUtils;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.SysRole;
import com.cloud.model.user.SysUser;
import com.cloud.user.feign.SmsClient;
import com.cloud.user.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@Api(value = "用户操作", tags = {"userController"})
@SessionAttributes("captchaCode")
public class UserController {

    @Autowired
    private SysUserService appUserService;

    /**
     * 当前登录用户 LoginAppUser
     */
    @GetMapping("/users/current")
    public LoginAppUser getLoginAppUser() {
        return AppUserUtil.getLoginAppUser();
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

}
