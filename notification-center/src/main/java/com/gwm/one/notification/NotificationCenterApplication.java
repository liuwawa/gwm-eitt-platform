package com.gwm.one.notification;

import com.gwm.one.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 通知中心
 * 
 * @author lz
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.gwm.one.*")
@Import(value = {SwaggerConfig.class})
public class NotificationCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationCenterApplication.class, args);
	}

}