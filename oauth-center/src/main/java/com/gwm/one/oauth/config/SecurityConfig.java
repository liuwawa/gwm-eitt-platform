package com.gwm.one.oauth.config;

import com.gwm.one.common.constants.PermitAllUrl;
import com.gwm.one.oauth.handler.BootFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * spring security配置
 *
 * @author lz
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BootFailureHandler handler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 全局用户信息<br>
     * 方法上的注解@Autowired的意思是，方法的参数的值是从spring容器中获取的<br>
     * 即参数AuthenticationManagerBuilder是spring中的一个Bean
     *
     * @param auth 认证管理
     * @throws Exception 用户认证异常信息
     */
    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 认证管理
     *
     * @return 认证管理对象
     * @throws Exception 认证异常信息
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * http安全配置
     *
     * @param http http安全对象
     * @throws Exception http安全异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
				.antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url
				.anyRequest().authenticated().and()
				.httpBasic().and().csrf().disable();*/


        http
                // 必须配置，不然OAuth2的http配置不生效----不明觉厉
                .requestMatchers()
                .antMatchers("/auth/login", "/auth/authorize", "/oauth/authorize")
                .and()
                .authorizeRequests()
                // 自定义页面或处理url是，如果不配置全局允许，浏览器会提示服务器将页面转发多次
                .antMatchers(PermitAllUrl.permitAllUrl("/auth/login", "/auth/error", "/auth/authorize", "/oauth/authorize"))
                .permitAll()
                .anyRequest()
                .authenticated();

        // 表单登录
        http.formLogin()
                .failureHandler(handler)
                // 页面
                .loginPage("/auth/login")
                // 登录处理url
                .loginProcessingUrl("/auth/authorize");

        http.httpBasic().disable();

    }

}
