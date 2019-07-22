package com.cloud.gateway.controller;

import com.cloud.common.utils.IPUtil;
import com.cloud.common.utils.RedisUtils;
import com.cloud.enums.ResponseStatus;
import com.cloud.gateway.feign.LogClient;
import com.cloud.gateway.feign.Oauth2Client;
import com.cloud.gateway.feign.UserClient;
import com.cloud.model.log.Log;
import com.cloud.model.log.constants.LogModule;
import com.cloud.model.oauth.SystemClientInfo;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.utils.ZuulUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.cloud.common.constants.SysConstants.PAST;
import static com.cloud.gateway.config.UserInterceptor.USER_CODE;

//import com.cloud.gateway.utils.SystemUtils;
//import com.cloud.gateway.utils.IPUtil;

/**
 * 登陆、刷新token、退出
 *
 * @author lz
 */
@Slf4j
@RestController
@Api(value = "登陆、刷新token、退出", tags = {"登陆、刷新token、退出 TokenController"})
public class TokenController {
    @Autowired
    private Oauth2Client oauth2Client;
    @Autowired
    private UserClient userClient;
    @Autowired
    private LogClient logClient;
    @Autowired
    private RedisUtils redisUtils;

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
    @ApiOperation(value = "根据用户名登录")
    public Map<String, Object> login(@ApiParam(value = "用户名", required = true) String username, @ApiParam(value = "密码", required = true) String password, HttpServletRequest request, HttpServletResponse response) {
        getOut(username, request);

        Map<String, String> parameters = initOauthParam();
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddr(request);
        saveLoginLog(username, "用户名密码登陆", ipAddress);
        //加入errorCode和message
        ZuulUtils.initZuulResponseForCode(tokenInfo, ResponseStatus.RESPONSE_SUCCESS);

        initTokenCookie(username, request, response, tokenInfo);

        return tokenInfo;
    }

    /**
     * 加入一个登陆后的token
     *
     * @param username
     * @param request
     * @param response
     * @param tokenInfo
     */
    private void initTokenCookie(String username, HttpServletRequest request, HttpServletResponse response, Map<String, Object> tokenInfo) {
        if ("10000".equals(String.valueOf(tokenInfo.get("errorCode")))) {
            // 验证用户是否是登陆状态
            String token = UUID.randomUUID().toString().replace("-", "");
            String remoteAddr = IPUtil.getIpAddr(request);
            String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String val = token + "@" + remoteAddr + "@" + loginTime + "@" + tokenInfo.get("access_token");
            redisUtils.delString(USER_CODE + username);
            redisUtils.setString(USER_CODE + username, val);
            Cookie cookie = new Cookie("token", username + "_" + token);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    /**
     * 查看用户是不是2次登陆的
     *
     * @param username
     * @param request
     */
    private void getOut(String username, HttpServletRequest request) {
        //如果需要踢出用户，先踢再登陆
        if ("1".equals(request.getParameter("out"))) {
            if (username != null) {
                String tok = redisUtils.getString(USER_CODE + username);
                String[] split = tok.split("@");
                if (split != null && split.length == 4) {
                    if (redisUtils.getString(PAST + username) != null) {
                        redisUtils.delString(PAST + username);
                    }
                    redisUtils.setString(PAST + username, split[3]);
                    //oauth2Client.removeToken(split[3]);
                }
            }
        }
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
    @ApiOperation(value = "短信登录")
    public Map<String, Object> smsLogin(@ApiParam(value = "手机号", required = true) String phone, @ApiParam(value = "验证码redis key", required = true) String key, @ApiParam(value = "验证码", required = true) String code, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> parameters = initOauthParam();

        SysUser appUser = userClient.findByPhone(phone);

        getOut(appUser.getUsername(), request);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put("username", appUser.getUsername() + "|" + CredentialType.PHONE.name() + "|" + key + "|" + code + "|"
                + DigestUtils.md5Hex(key + code));
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", phone);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);


        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddr(request);
        saveLoginLog(phone, "手机号短信登陆", ipAddress);
        //加入errorCode和message
        ZuulUtils.initZuulResponseForCode(tokenInfo, ResponseStatus.RESPONSE_SUCCESS);
        initTokenCookie(appUser.getUsername(), request, response, tokenInfo);
        return tokenInfo;
    }

    /**
     * 微信登录
     *
     * @return
     */
    @PostMapping("/sys/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode, HttpServletRequest request) {
        Map<String, String> parameters = initOauthParam();
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put("username", openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", tempCode);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        //获取登录真实ip
        String ipAddress = IPUtil.getIpAddr(request);
        saveLoginLog(openid, "微信登陆", ipAddress);

        return tokenInfo;
    }

    /**
     * 初始化 oauth认证基本参数
     *
     * @return
     */
    private Map<String, String> initOauthParam() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        return parameters;
    }

    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark, String ipAddress) {
        log.info("{}登陆", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                Log log = Log.builder().username(username).module(LogModule.LOGIN).remark(remark).ip(ipAddress).createTime(new Date())
                        .build();
                logClient.saveLog(log);
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
    @GetMapping("/sys/refresh_token")
    @ApiOperation(value = "系统刷新refresh_token")
    public Map<String, Object> refresh_token(@ApiParam(value = "refresh_token", required = true)String refresh_token) {
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
    @ApiOperation(value = "登出")
    public void logout(@ApiParam(value = "access_token", required = true) String access_token, @ApiParam(value = "username", required = true) String username, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        if (username != null) {
            redisUtils.delString(USER_CODE + username);
            redisUtils.delString(PAST + username);
        }
        oauth2Client.removeToken(access_token);
    }

}
