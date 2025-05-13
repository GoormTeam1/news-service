package edu.goorm.news_service.controller;

import edu.goorm.news_service.service.LearningStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class LearningStatusController {

    private final LearningStatusService learningStatusService;

    @GetMapping("/status/{newsId}")
    public ResponseEntity<String> getLearningStatus(
            @RequestHeader(value = "X-User-Email", required = false) String userEmail,
            @PathVariable Long newsId) {

        String status = learningStatusService.getLearningStatusForNews(userEmail, newsId);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }
} 
