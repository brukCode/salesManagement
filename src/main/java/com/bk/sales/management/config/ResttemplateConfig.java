package com.bk.sales.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration  // ✅ Add this annotation
public class ResttemplateConfig {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
