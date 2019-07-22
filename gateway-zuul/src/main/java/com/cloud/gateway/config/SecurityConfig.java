package com.cloud.gateway.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

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
		http.cors().disable();

		/**
		 * always – 如果session不存在总是需要创建；
		 * ifRequired – 仅当需要时，创建session(默认配置)；
		 * never – 框架从不创建session，但如果已经存在，会使用该session ；
		 * stateless – Spring Security不会创建session，或使用session；
		 * ---------------------
		 * 作者：neweastsun
		 * 来源：CSDN
		 * 原文：https://blog.csdn.net/neweastsun/article/details/79371175
		 * 版权声明：本文为博主原创文章，转载请附上博文链接！
		 */
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

		/*
		 * "migrateSession"，即认证时，创建一个新http session，原session失效，属性从原session中拷贝过来
		 * “none”，原session保持有效；
		 * “newSession”，新创建session，且不从原session中拷贝任何属性。
		 */
		http.sessionManagement().sessionFixation().none();
	}
}