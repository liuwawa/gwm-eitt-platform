package com.cloud.personnel;

import com.cloud.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 个人中心
 *
 * @author lz
 */
@EnableDiscoveryClient
@ComponentScan("com.cloud.*")
@SpringBootApplication
@Import(value = {SwaggerConfig.class})
public class PersonnelCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelCenterApplication.class, args);
    }

}
