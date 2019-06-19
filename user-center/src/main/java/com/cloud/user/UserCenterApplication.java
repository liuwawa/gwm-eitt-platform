package com.cloud.user;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 用户中心
 * 
 * @author lz
 *
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@PropertySource(value = {"classpath:provider.properties"})
@Import(value = {SwaggerConfig.class})
@ComponentScan("com.cloud.*")
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

}