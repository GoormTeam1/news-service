package edu.goorm.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import edu.goorm.news_service.entity.News;

/**
 * 기사 정보를 전달하기 위한 DTO 클래스 
 * 엔티티와 클라이언트 간의 데이터 전송에 사용됨
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDto {
    /** 기사 ID */
    private Long id;
    
    /** 기사 제목 */
    private String title;
    
    /** 기사 원문 내용 */
    private String fullText;
    
    /** 기사 요약 */
    private String summary;
    
    /** 기사 출처 */
    private String source;

    /** 기사 이미지 URL */
    private String imageLink;
    
    /** 기사 카테고리 */
    private String category;
    
    /** 기사 발행 일시 */
    private LocalDateTime publishedAt;
    
    /** 기사 생성 일시 */
    private LocalDateTime createdAt;

    /**
     * News 엔티티를 NewsDto로 변환하는 메서드
     * @param news 변환할 News 엔티티
     * @return 변환된 NewsDto 객체
     */
    public static NewsDto fromEntity(News news) {
        return NewsDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .fullText(news.getFullText())
                .summary(news.getSummary())
                .source(news.getSource())
                .category(news.getCategory())
                .publishedAt(news.getPublishedAt())
                .imageLink(news.getImageLink())
                .createdAt(news.getCreatedAt())
                .build();
    }
}
