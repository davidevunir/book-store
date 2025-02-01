package com.bs.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BsOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsOrdersApplication.class, args);
	}

}
