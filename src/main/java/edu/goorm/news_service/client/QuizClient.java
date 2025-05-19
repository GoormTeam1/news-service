package edu.goorm.news_service.client;

import edu.goorm.news_service.dto.WrongQuizDto;
import edu.goorm.news_service.config.FeignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
    name = "quiz-service",
    url = "http://localhost:8083",
    configuration = FeignConfig.class
)
public interface QuizClient {
    @GetMapping("/api/quiz/wrong")
    List<WrongQuizDto> getWrongQuizzes(@RequestHeader("X-User-Email") String userEmail);
}