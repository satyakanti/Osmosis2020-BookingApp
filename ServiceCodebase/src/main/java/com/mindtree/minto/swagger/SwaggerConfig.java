/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * SwaggerConfig.java Created On: Feb 22, 2020 Created By: M1026329
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage("com.mindtree.minto")).paths(PathSelectors.any())
            .build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Minto App", "Minto App for Osmosis 2020", "1.0", "Terms of service",
            new Contact("Navneet Thakur", "www.mindtree.com", "navneet.thakur@mindtree.com"),
            "Licensed to Mindtree Ltd", "<API License Here>", Collections.emptyList());
    }
}
