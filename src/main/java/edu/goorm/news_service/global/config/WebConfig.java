package edu.goorm.news_service.global.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 게이트웨이에서 처리하는 경우 주석처리
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")                // 모든 경로에 대해
                .allowedOrigins("*")              // 모든 Origin 허용
                .allowedMethods("*")              // 모든 HTTP 메서드 허용
                .allowedHeaders("*")              // 모든 헤더 허용
                .allowCredentials(false);          // 인증 정보 허용
    }
}
