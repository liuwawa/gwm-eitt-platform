package com.gwm.one.log.config;

import com.gwm.one.common.constants.PermitAllUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务配置
 *
 * @author lz
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//前置校验 主要是负责调用接口的时候 是否有权限访问
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /**
     * url前缀
     */
    @Value("${file.local.prefix}")
    private String localFilePrefix;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anons = {"/logs-anon/**", "/files-anon/**", localFilePrefix + "/**", "/notification-anon/**"};
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl(anons)).permitAll() // 放开权限的url 匿名的接口
                .anyRequest().authenticated().and().httpBasic();
    }

}
