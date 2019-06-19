package com.cloud.oauth.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lz
 * @create 2018/11/1 11:44
 * @description
 * @modify
 */
@Controller
@SessionAttributes("authorizationRequest")
public class BootGrantController {

    @RequestMapping("/custom/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");

        ModelAndView view = new ModelAndView();
        view.setViewName("base-grant");

        view.addObject("clientId", authorizationRequest.getClientId());
        /*Set<String> set = authorizationRequest.getScope();
        String scope = null;
        if(!set.isEmpty()){
            scope = set.iterator().next();
        }
        view.addObject("scope", scope);*/

        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        model.put("scopeList", scopeList);

        return view;
    }

}
