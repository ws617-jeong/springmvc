package com.test.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages = "com.test.tasks")
@EnableWebMvc
public class HealthcheckConfiguration implements WebMvcConfigurer {

	@Autowired
	private HealthcheckInterceptor healthcheckInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(healthcheckInterceptor)
		.addPathPatterns("/*");
	}
	
	@Bean
	public ObjectMapper jsonMapper() {
		return new ObjectMapper();
	}
}
