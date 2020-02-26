/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * MintoAppApplication.java Created On: Feb 22, 2020 Created By: M1026329
 */
@SpringBootApplication
public class MintoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MintoAppApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}