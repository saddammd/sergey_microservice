package com.microservice.practical.demo.user.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.microservice.practical.demo.user.microservice.service.UserService;
import com.microservice.practical.demo.user.microservice.service.UserServiceImpl;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public UserService getUserService() {
		return new UserServiceImpl();
	}

}
