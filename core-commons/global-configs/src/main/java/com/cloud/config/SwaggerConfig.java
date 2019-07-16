package com.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger文档配置
 * 
 * @author lz
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket docket() {
		//添加header参数
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList();
		tokenPar.name("Authorization")
                .description("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header").defaultValue("Bearer ")
                .required(false).build();
		pars.add(tokenPar.build());

		return new Docket(DocumentationType.SWAGGER_2).groupName("认证中心swagger接口文档")
				.apiInfo(getApiInfo())
				.select()
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
	}

    /**
     * swagger信息填充
     * @return
     */
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("认证中心swagger接口文档")
                .contact(
                        new Contact("lz", "", "greatwall@163.com"))
                .version("1.0")
                .build();
    }
}
