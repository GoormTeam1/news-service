package edu.goorm.news_service.domain.controller;

import edu.goorm.news_service.domain.service.LearningStatusService;
import edu.goorm.news_service.global.logger.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class LearningStatusController {

    private final LearningStatusService learningStatusService;

    @GetMapping("/status/{newsId}")
    public ResponseEntity<String> getLearningStatus(
        @RequestHeader(value = "X-User-Email", required = false) String userEmail,
        @PathVariable Long newsId,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();

        String status = learningStatusService.getLearningStatusForNews(userEmail, newsId);

        long end = System.currentTimeMillis();
        int responseStatus = (status == null) ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value();

        CustomLogger.logRequest(
            "GET_LEARNING_STATUS",
            "/api/news/status/" + newsId,
            "GET",
            userEmail,
            request,
            responseStatus,
            end - start
        );

        if (status == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(status);
    }
}
