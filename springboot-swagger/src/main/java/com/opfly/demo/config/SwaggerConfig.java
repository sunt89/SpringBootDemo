package com.opfly.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()                                  
                .apis(RequestHandlerSelectors.basePackage("com.opfly.demo.controller"))              
                .paths(PathSelectors.any())                          
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Spring Boot集成Swagger测试")
                        .description("Spring Boot集成Swagger测试")
                        .version("1.0")
                        .contact(new Contact("sun_t89","https://opfly.cn","sun_t89@126.com"))
                        .license("The Apache License")
                        .licenseUrl("https://opfly.cn")
                        .build()); 
    }
}
