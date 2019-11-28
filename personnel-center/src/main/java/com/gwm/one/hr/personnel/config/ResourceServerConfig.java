package com.gwm.one.hr.personnel.config;

import com.gwm.one.common.constants.PermitAllUrl;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(
                (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().antMatchers(PermitAllUrl
                .permitAllUrl("/personnels-anon/**")).permitAll();
    }

}
