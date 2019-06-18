package com.cloud.backend;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 管理后台
 * 
 * @author lz 10000@163.com
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(value = {SwaggerConfig.class})
public class ManageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageBackendApplication.class, args);
	}

}