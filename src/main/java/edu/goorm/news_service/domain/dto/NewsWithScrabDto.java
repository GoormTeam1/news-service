package edu.goorm.news_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;
import java.time.LocalDate;

import edu.goorm.news_service.domain.entity.News;

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
    private LocalDate publishedAt;
    private LocalDate createAt;
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
