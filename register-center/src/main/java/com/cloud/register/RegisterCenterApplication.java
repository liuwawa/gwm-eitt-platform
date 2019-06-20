package com.cloud.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 注册中心<br>
 * 访问地址 http://localhost:8761
 * 
 * @author lz
 *
 */
@EnableEurekaServer
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@PropertySource(value = {"classpath:provider.properties"})
public class RegisterCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterCenterApplication.class, args);
	}

}