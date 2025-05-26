package edu.goorm.news_service.domain.dto;

import edu.goorm.news_service.domain.entity.News;
import java.time.LocalDateTime;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationNewsDto {
  /** 기사 ID */
  private Long id;

  /** 기사 제목 */
  private String title;

  /** 기사 이미지 URL */
  private String image;

  /** 기사 카테고리 */
  private String category;

  /** 기사 발행 일시 */
  private LocalDate publishedAt;

  public RecommendationNewsDto(News news) {
    this.id = news.getId();
    this.title = news.getTitle();
    this.image = news.getImage();
    this.category = news.getCategory();
    this.publishedAt = news.getPublishedAt();
  }
}
