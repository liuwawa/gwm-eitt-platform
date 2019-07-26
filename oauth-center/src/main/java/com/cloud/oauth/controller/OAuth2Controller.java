package com.cloud.oauth.controller;

import com.cloud.model.log.Log;
import com.cloud.model.log.constants.LogModule;
import com.cloud.oauth.feign.LogClient;
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
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    /**
     * 注销登陆/退出
     * @param accessToken
     */
    @SuppressWarnings("checkstyle:ParameterName")
    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String accessToken) {
        boolean flag = tokenServices.revokeToken(accessToken);
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
                // do nothing
            }

        });
    }

}
