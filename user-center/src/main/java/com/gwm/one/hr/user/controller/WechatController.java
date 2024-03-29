package com.gwm.one.hr.user.controller;

import com.gwm.one.common.utils.AppUserUtil;
import com.gwm.one.hr.user.service.WechatService;
import com.gwm.one.model.hr.user.SysUser;
import com.gwm.one.model.hr.user.WechatUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequestMapping("/wechat")
@ApiIgnore //swagger文档 忽略此接口类 后期加入微信登录 注释掉此注解即可
public class WechatController {

    @Autowired
    private WechatService wechatService;

    /**
     * 引导到授权
     *
     * @param app
     * @param request
     * @param toUrl   授权后，跳转的页面url，注意url要转义
     * @return
     */
    @GetMapping("/{app}")
    public RedirectView toWechatAuthorize(@PathVariable String app, HttpServletRequest request,
                                          @RequestParam String toUrl) throws UnsupportedEncodingException {
        String url = wechatService.getWechatAuthorizeUrl(app, request, toUrl);

        return new RedirectView(url);
    }

    /**
     * 授权后，微信跳转到此接口
     *
     * @return
     */
    @GetMapping(value = "/{app}/back", params = {"code", "state"})
    public RedirectView wechatBack(HttpServletRequest request, @PathVariable String app, String code, String state,
                                   @RequestParam String toUrl) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("code不能为空");
        }

        if (StringUtils.isBlank(state)) {
            throw new IllegalArgumentException("state不能为空");
        }

        WechatUserInfo wechatUserInfo = wechatService.getWechatUserInfo(app, request, code, state);

        toUrl = wechatService.getToUrl(toUrl, wechatUserInfo);

        return new RedirectView(toUrl);
    }

    /**
     * 微信绑定用户
     *
     * @param tempCode
     * @param openid
     */
    @PostMapping(value = "/binding-user", params = {"tempCode", "openid"})
    public void bindingUser(String tempCode, String openid) {
        SysUser appUser = AppUserUtil.getLoginAppUser();
        if (appUser == null) {
            throw new IllegalArgumentException("非法请求");
        }

        log.info("绑定微信和用户：{},{},{}", appUser, openid, tempCode);
        wechatService.bindingUser(appUser, tempCode, openid);
    }

    /**
     * 微信登陆校验
     *
     * @param tempCode
     * @param openid
     */
    @GetMapping(value = "/login-check", params = {"tempCode", "openid"})
    public void wechatLoginCheck(String tempCode, String openid) {
        wechatService.checkAndGetWechatUserInfo(tempCode, openid);
    }
}
