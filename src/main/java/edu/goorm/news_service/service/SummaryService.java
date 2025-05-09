package edu.goorm.news_service.service;

import org.springframework.stereotype.Service;
import edu.goorm.news_service.dto.SummaryDto;
import edu.goorm.news_service.entity.Summary;
import edu.goorm.news_service.repository.SummaryRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 요약 정보를 관리하는 서비스 클래스
 */
@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;

    public SummaryService(SummaryRepository summaryRepository) {
        this.summaryRepository = summaryRepository;
    }

    /**
     * 주어진 기사 ID와 레벨에 해당하는 요약 정보를 조회
     */
    public SummaryDto getSummary(Long newsId, String level) {
        Summary summary = summaryRepository.findByNewsIdAndLevel(newsId, level);
        if (summary == null) {
            throw new IllegalArgumentException("요약 정보를 찾을 수 없습니다. newsId=" + newsId + ", level=" + level);
        }
        return SummaryDto.fromEntity(summary);
    }

    /**
     * 주어진 기사 ID에 해당하는 모든 요약 정보를 조회
     */
    public List<SummaryDto> getSummaries(Long newsId) {
        List<Summary> summaries = summaryRepository.findByNewsId(newsId);
        return summaries.stream()
                .map(SummaryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
