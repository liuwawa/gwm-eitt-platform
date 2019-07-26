package com.cloud.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lz
 * @create time 2018/10/9  15:09
 * @description
 * @modify by
 * @modify time
 **/
@Controller
public class BaseMainController {
    @GetMapping("/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("loginProcessUrl", "/auth/authorize");
        return "base-login";
    }

    @GetMapping("/auth/error")
    public String error401(Model model) {
        return "401";
    }

    @GetMapping("/")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        if (code == null) {
            response.setStatus(401);
            return "401";
        }
        model.addAttribute("codeId", code);
        return "200";
    }
}
