package com.cloud.log;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 日志中心
 * 
 * @author lz
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.cloud.*")
@Import(value = {SwaggerConfig.class})
public class LogCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
	}

}