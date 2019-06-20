package com.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置中心
 * 
 * @author lz
 *
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@PropertySource(value = {"classpath:provider.properties"})
public class ConfigCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigCenterApplication.class, args);
	}

}
