/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * MintoAppApplication.java Created On: Feb 22, 2020 Created By: M1026329
 */
@SpringBootApplication
//@EnableAsync
public class MintoAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MintoAppApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	/*
	 * @Bean public Executor taskExecutor() { ThreadPoolTaskExecutor executor = new
	 * ThreadPoolTaskExecutor(); executor.setCorePoolSize(2);
	 * executor.setMaxPoolSize(2); executor.setQueueCapacity(500);
	 * executor.setThreadNamePrefix("GithubLookup-"); executor.initialize(); return
	 * executor; }
	 */

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MintoAppApplication.class);
	}
}
