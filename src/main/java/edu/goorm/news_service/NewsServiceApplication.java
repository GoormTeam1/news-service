package edu.goorm.news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "edu.goorm.news_service.domain.client")
public class NewsServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

}
