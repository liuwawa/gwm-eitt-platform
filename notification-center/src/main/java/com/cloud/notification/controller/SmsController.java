package com.cloud.notification.controller;


import com.cloud.common.utils.PhoneUtil;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Page;
import com.cloud.notification.model.Sms;
import com.cloud.notification.model.VerificationCode;
import com.cloud.notification.service.SmsService;
import com.cloud.notification.service.VerificationCodeService;
import com.cloud.notification.utils.JSONHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class SmsController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 发送手机短信
     *
     * @param phone   手机号
     * @param content 发送内容
     * @return 发送结果
     */
    @PostMapping(value = "/notification-anon/codes", params = {"phone", "content"})
    public Map sendSmsVerificationCode(String phone, String content) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        String account = "344932";// 用户名（必填）
        String password = "FuG7HB8b";// 密码（必填）
        String sign = "【长城汽车】"; // 短信签名（必填）
        String subcode = ""; // 子号码（选填）
        String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
        String sendtime = ""; // 定时发送时间（可选）
        Map<Integer, String> result = new HashMap<>();
        try {
            JSONHttpClient jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
            jsonHttpClient.setRetryCount(1);
            // 调用sendSms方法
            String sendhRes = jsonHttpClient.sendSms(account, password, phone, content, sign, subcode);
            log.info("提交单条普通短信响应：" + sendhRes);
            result.put(ResponseStatus.RESPONSE_PHONE_MESSAGE_SUCCESS.code, ResponseStatus.RESPONSE_PHONE_MESSAGE_SUCCESS.message);
        } catch (Exception e) {
            log.error("短信服务异常", e);
            result.put(ResponseStatus.RESPONSE_PHONE_MESSAGE_ERROR.code, ResponseStatus.RESPONSE_PHONE_MESSAGE_ERROR.message);
        }
        return result;
    }

    /**
     * 根据验证码和key获取手机号
     *
     * @param key
     * @param code
     * @param delete 是否删除key
     * @param second 不删除时，可重置过期时间（秒）
     * @return
     */
    @GetMapping(value = "/notification-anon/internal/phone", params = {"key", "code"})
    public String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second) {
        return verificationCodeService.matcheCodeAndGetPhone(key, code, delete, second);
    }

    @Autowired
    private SmsService smsService;

    /**
     * 查询短信发送记录
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('sms:query')")
    @GetMapping("/sms")
    public Page<Sms> findSms(@RequestParam Map<String, Object> params) {
        return smsService.findSms(params);
    }
}
