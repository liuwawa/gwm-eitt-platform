package com.cloud.notification;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 通知中心
 * 
 * @author lz
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(value = {SwaggerConfig.class})
public class NotificationCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationCenterApplication.class, args);
	}

}