package edu.goorm.news_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.goorm.news_service.domain.entity.News;

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
    @JsonProperty("full_text")
    private String fullText;
    
    /** 기사 출처 */
    @JsonProperty("source_link")
    private String sourceLink;

    /** 기사 이미지 URL */
    private String image;
    
    /** 기사 카테고리 */
    private String category;
    
    /** 기사 발행 일시 */
    @JsonProperty("published_at")
    private LocalDate publishedAt;
    
    /** 기사 생성 일시 */
    @JsonProperty("create_at")
    private LocalDate createAt;

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
                .sourceLink(news.getSourceLink())
                .category(news.getCategory())
                .publishedAt(news.getPublishedAt())
                .image(news.getImage())
                .createAt(news.getCreateAt())
                .build();
    }
}
