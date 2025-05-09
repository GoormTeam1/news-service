package edu.goorm.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;

import edu.goorm.news_service.entity.News;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsWithScrabDto {
    private Long id;
    private String title;
    private String fullText;
    private String category;
    private String sourceLink;
    private String image;
    private LocalDateTime publishedAt;
    private LocalDateTime createAt;
    private boolean isScrabbed;

    public static NewsWithScrabDto fromEntity(News news, boolean isScrabbed) {
        return NewsWithScrabDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .fullText(news.getFullText())
                .category(news.getCategory())
                .sourceLink(news.getSourceLink())
                .image(news.getImage())
                .publishedAt(news.getPublishedAt())
                .createAt(news.getCreateAt())
                .isScrabbed(isScrabbed)
                .build();
    }
}
