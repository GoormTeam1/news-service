package edu.goorm.news_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import edu.goorm.news_service.entity.Summary;


/**
 * 요약 정보를 전달하는 DTO 클래스
 */
@Getter
@Setter
@Builder
public class SummaryDto {
    private Long summaryId;
    private Long newsId;
    private String level;
    private String summary;

    /**
     * Summary 엔티티를 SummaryDto로 변환하는 메서드
     * @param summary 변환할 Summary 엔티티
     * @return 변환된 SummaryDto 객체
     */
    public static SummaryDto fromEntity(Summary summary) {
        return SummaryDto.builder()
                .summaryId(summary.getSummaryId())
                .newsId(summary.getNewsId())
                .level(summary.getLevel())
                .summary(summary.getSummary())
                .build();
    }
}
