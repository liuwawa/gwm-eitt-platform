package com.cloud.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring security配置
 * 
 * @author lz
 * 
 */
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		/*
		* 使用 X-Frame-Options 有三个可选的值：

			DENY：浏览器拒绝当前页面加载任何Frame页面

			SAMEORIGIN：frame页面的地址只能为同源域名下的页面

			ALLOW-FROM：origin为允许frame加载的页面地址
		* */
		http.headers().frameOptions().sameOrigin();//设置frame 用 same方式
		http.cors();
	}

}