package com.cloud.user.config;

import com.cloud.common.constants.PermitAllUrl;
import com.cloud.common.entry.AuthExceptionEntryPoint;
import com.cloud.common.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static com.cloud.common.constants.Messages.APP;

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
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint(APP)).accessDeniedHandler(new CustomAccessDeniedHandler())
                .and().authorizeRequests()
                .antMatchers(PermitAllUrl.permitAllUrl("/users-anon/**", "/wechat/**", "/phone-anon/**")).permitAll() // 放开权限的url
                .anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint(APP)).accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
