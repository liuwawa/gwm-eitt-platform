package com.cloud.gateway.controller;

import com.cloud.enums.ResponseStatus;
import com.cloud.gateway.feign.LogClient;
import com.cloud.gateway.feign.Oauth2Client;
import com.cloud.gateway.feign.UserClient;
import com.cloud.gateway.utils.IPUtil;
import com.cloud.model.log.Log;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.oauth.SystemClientInfo;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.utils.ZuulUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 登陆、刷新token、退出
 *
 * @author lz
 */
@Slf4j
@RestController
public class TokenController {
    @Autowired
    private Oauth2Client oauth2Client;
    @Autowired
    private UserClient userClient;
    @Autowired
    private LogClient logClient;

    /**
     * @return
     */
    @GetMapping("/sys/code")
    public Map<String, Object> getCode(@RequestParam Map<String, String> params) {
        ModelAndView tokenInfo = oauth2Client.postCodeGrant(params);
        System.out.println();
        return null;
    }

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(String username, String password, HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
//		parameters.put("username", username);
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddress(request);
        saveLoginLog(username, "用户名密码登陆",ipAddress);
        //加入errorCode和message
        ZuulUtils.initZuulResponseForCode(tokenInfo, ResponseStatus.RESPONSE_SUCCESS);
        return tokenInfo;
    }

    /**
     * 短信登录
     *
     * @param phone
     * @param key
     * @param code
     * @return
     */
    @PostMapping("/sys/login-sms")
    public Map<String, Object> smsLogin(String phone, String key, String code,HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);

        SysUser appUser = userClient.findByPhone(phone);
       /* if (appUser != null && StringUtils.isAllBlank(appUser.getPassword(),appUser.getUsername())){
            parameters.put("username", appUser.getUsername() + "|" + CredentialType.USERNAME.name());
            parameters.put("password", appUser.getPassword());
        }*/
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put("username", appUser.getUsername() + "|" + CredentialType.PHONE.name() + "|" + key + "|" + code + "|"
                + DigestUtils.md5Hex(key + code));
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", phone);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);


        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddress(request);
        saveLoginLog(phone, "手机号短信登陆",ipAddress);

        return tokenInfo;
    }

    /**
     * 微信登录
     *
     * @return
     */
    @PostMapping("/sys/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode,HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put("username", openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", tempCode);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddress(request);
        saveLoginLog(openid, "微信登陆",ipAddress);

        return tokenInfo;
    }

    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark,String ipAddress) {
        log.info("{}登陆", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                Log log = Log.builder().username(username).module(LogModule.LOGIN).remark(remark).ip(ipAddress).createTime(new Date())
                        .build();
                logClient.save(log);
            } catch (Exception e) {
                // do nothing
            }

        });
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/sys/refresh_token")
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refresh_token);

        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @GetMapping("/sys/logout")
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        oauth2Client.removeToken(access_token);
    }
}
