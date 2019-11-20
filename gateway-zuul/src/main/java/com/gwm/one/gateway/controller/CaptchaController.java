package com.gwm.one.gateway.controller;

import com.gwm.one.common.utils.VerifyCodeUtils;
import com.gwm.one.common.vo.ResultVo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

/***
 * 验证码
 */
@Slf4j
@RestController
@Api(value = "登录验证码", tags = {"登录验证码"})
public class CaptchaController {

    public static final int DELAY = 60 * 1000;
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    private final String SESSION_KEY = "SESSION_KEY";

    /**
     * 验证码生成
     */
    @GetMapping("/users-anon/captcha")
    @ApiOperation(value = "生成验证码")
    public void captchaInit(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        log.info("验证码:{}", code);

        sessionStrategy.setAttribute(new ServletWebRequest(request, response), SESSION_KEY, code);
        removeAttrbute(new ServletWebRequest(request, response), SESSION_KEY);
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
     * @param code 前台传值
     *             判断验证码
     */
    @GetMapping("/users-anon/checkCaptcha/{code}")
    @ApiOperation(value = "校验验证码")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "path", name = "code", value = "前台传递的验证码", required = true, dataType = "String")
    )
    public ResultVo checkCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String code) {
        if (StringUtils.isBlank(code)) {
            throw new IllegalArgumentException("请输入验证码！");
        }
        String trueCode = (String) sessionStrategy.getAttribute(new ServletWebRequest(request, response), SESSION_KEY);
        if (StringUtils.isBlank(trueCode)) {
            throw new IllegalArgumentException("验证码超时!");
        }
        log.info("session中的,code:{}", trueCode);
        log.info("输入的,code:{}", code);
        if (!code.equalsIgnoreCase(trueCode)) {
            throw new IllegalArgumentException("验证码错误！");
        }
        log.info("验证码正确");
        return ResultVo.builder().code(20000).msg("验证码校验成功!").data(null).build();
    }


    /**
     * 设置1分钟后删除session中的验证码
     *
     * @param attrName
     */
    private void removeAttrbute(final ServletWebRequest request, final String attrName) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sessionStrategy.removeAttribute(request, attrName);
                timer.cancel();
            }
        }, DELAY);
    }

}
