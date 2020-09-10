package br.gov.sp.iamspe.mensageria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration

public class RestTemplateConfig {
    
	// @Bean
	// @LoadBalanced
	// public RestTemplate restTemplateLoad(){
	// 	System.setProperty("proxyHost", "10.1.6.100");
    //     System.setProperty("proxyPort", "80");
	// 	return new RestTemplate();
	// }

	@Bean
	public RestTemplate restTemplate(){
		System.setProperty("proxyHost", "10.1.6.100");
        System.setProperty("proxyPort", "80");
		return new RestTemplate();
	}
}