package com.gwm.one.hr.personnel;

import com.gwm.one.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * 个人中心
 *
 * @author lz
 */
@EnableDiscoveryClient
@ComponentScan("com.gwm.one.*")
@PropertySource(value = {"classpath:provider.properties"})
@SpringBootApplication
@Import(value = {SwaggerConfig.class})
public class PersonnelCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelCenterApplication.class, args);
    }

}
