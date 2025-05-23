package edu.goorm.news_service.domain.controller;

import edu.goorm.news_service.domain.dto.SummaryDto;
import edu.goorm.news_service.domain.service.SummaryService;
import edu.goorm.news_service.global.logger.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 요약 정보를 관리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("/{newsId}/{level}")
    public ResponseEntity<SummaryDto> getSummary(
        @PathVariable Long newsId,
        @PathVariable String level,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();
        SummaryDto summary = summaryService.getSummary(newsId, level);
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "GET_SUMMARY_BY_LEVEL",
            "/api/summary/" + newsId + "/" + level,
            "GET",
            String.valueOf(newsId),
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<List<SummaryDto>> getSummaries(
        @PathVariable Long newsId,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();
        List<SummaryDto> summaries = summaryService.getSummaries(newsId);
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "GET_SUMMARIES_BY_NEWS_ID",
            "/api/summary/" + newsId,
            "GET",
            String.valueOf(newsId),
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/search/{summaryId}")
    public ResponseEntity<SummaryDto> getNewsId(
        @PathVariable Long summaryId,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();
        SummaryDto summary = summaryService.getSummaryBySummaryId(summaryId);
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "GET_SUMMARY_BY_ID",
            "/api/summary/search/" + summaryId,
            "GET",
            String.valueOf(summaryId),
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok(summary);
    }
}
