package edu.goorm.news_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackages = "edu.goorm.news_service.client")
@EnableScheduling
public class NewsServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(NewsServiceApplication.class, args);
	}

}
