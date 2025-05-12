package edu.goorm.news_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.goorm.news_service.dto.SummaryDto;
import edu.goorm.news_service.service.SummaryService;
import java.util.List;

/**
 * 요약 정보를 관리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/summary")
public class SummaryController {
    
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    /**
     * 주어진 기사 ID와 레벨에 해당하는 요약 정보를 조회하는 메서드
     * @param newsId 기사 ID
     * @param level 레벨
     * @return 요약 정보
     */
    @GetMapping("/{newsId}/{level}")
    public SummaryDto getSummary(@PathVariable Long newsId, @PathVariable String level) {
        return summaryService.getSummary(newsId, level);
    }

    /**
     * 주어진 기사 ID에 해당하는 모든 요약 정보를 조회하는 메서드
     * @param newsId 기사 ID
     * @return 요약 정보 목록
     */
    @GetMapping("/{newsId}")
    public List<SummaryDto> getSummaries(@PathVariable Long newsId) {
        return summaryService.getSummaries(newsId);
    }

    /**
     * 주어진 요약 ID에 해당하는 요약 정보를 조회하는 메서드
     * @param summaryId 요약 ID
     * @return 요약 정보
     */
    @GetMapping("/search/{summaryId}")
    public SummaryDto getNewsId(@PathVariable Long summaryId) {
        return summaryService.getSummaryBySummaryId(summaryId);
    }
      
}
