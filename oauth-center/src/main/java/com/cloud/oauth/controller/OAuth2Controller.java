package com.cloud.oauth.controller;

import com.cloud.model.log.Log;
import com.cloud.model.log.constants.LogModule;
import com.cloud.oauth.feign.LogClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
@Api(value = "oauth获取用户认证信息，登出", tags = "oauth获取用户认证信息，登出")
public class OAuth2Controller {
    @Autowired
    private ConsumerTokenServices tokenServices;
    @Autowired
    @Lazy
    private LogClient logClient;
    /**
     * 当前登陆用户信息<br>
     *
     * @return
     */
    @GetMapping("/user-me")
    @ApiOperation(value = "当前登陆用户信息")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    /**
     * 注销登陆/退出
     * @param access_token
     */
    @SuppressWarnings("checkstyle:ParameterName")
    @DeleteMapping(value = "/remove_token", params = "access_token")
    @ApiOperation("注销/退出登录")
    public void removeToken(@ApiParam(value = "access_token", required = true) String access_token) {
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            saveLogoutLog(authentication.getName());
        }
    }

    /**
     * 退出日志
     *
     * @param username
     */
    private void saveLogoutLog(String username) {
        log.info("{}退出", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                Log log = Log.builder().username(username).module(LogModule.LOGOUT).createTime(new Date()).build();
                logClient.saveLog(log);
            } catch (Exception e) {
                // TODO: do nothing
            }

        });
    }

}
