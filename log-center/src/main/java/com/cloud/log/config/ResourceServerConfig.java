package com.cloud.log.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.cloud.common.constants.PermitAllUrl;

/**
 * 资源服务配置
 * 
 * @author lz
 *
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//前置校验 主要是负责调用接口的时候 是否有权限访问
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl("/logs-anon/**")).permitAll() // 放开权限的url 匿名的接口
				.anyRequest().authenticated().and().httpBasic();
	}

}
