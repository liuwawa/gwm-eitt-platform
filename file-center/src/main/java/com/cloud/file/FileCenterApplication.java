package com.cloud.file;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * 文件中心
 * 
 * @author lz
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(value = {SwaggerConfig.class})
public class FileCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileCenterApplication.class, args);
	}

}