package com.cloud.oauth;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 认证中心
 *
 * @author lz
 */
@EnableFeignClients("com.cloud.oauth.feign")
@EnableDiscoveryClient
@SpringBootApplication
@PropertySource(value = {"classpath:provider.properties"})
@Import(value = {SwaggerConfig.class})
public class OAuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthCenterApplication.class, args);
    }

}
