package com.bs.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SpringBootApplication
public class BsOrdersApplication {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	public static void main(String[] args) {
		SpringApplication.run(BsOrdersApplication.class, args);
	}

}
