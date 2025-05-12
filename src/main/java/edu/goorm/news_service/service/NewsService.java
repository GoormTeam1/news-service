package edu.goorm.news_service.service;

import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.dto.NewsWithScrabDto;
import edu.goorm.news_service.dto.RecommendationNewsDto;
import edu.goorm.news_service.entity.ScrabId;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.repository.NewsRepository;
import edu.goorm.news_service.repository.ScrabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

  private final NewsRepository newsRepository;
  private final ScrabRepository scrabRepository;

  public Page<NewsDto> getAllNews(Pageable pageable) {
    return newsRepository.findAll(pageable).map(NewsDto::fromEntity);
  }

  public NewsDto getNewsById(Long id) {
    return newsRepository.findById(id).map(NewsDto::fromEntity)
        .orElseThrow(() -> new IllegalArgumentException("기사를 찾을 수 없습니다. ID=" + id));
  }

  public Page<NewsDto> searchNews(String keyword, Pageable pageable) {
    return newsRepository.findByTitleContainingOrFullTextContaining(keyword, keyword, pageable)
        .map(NewsDto::fromEntity);
  }

  public Page<NewsDto> getNewsByCategory(String category, Pageable pageable) {
    return newsRepository.findByCategory(category, pageable).map(NewsDto::fromEntity);
  }

  @Transactional
  public NewsDto createNews(News news) {
    News savedNews = newsRepository.save(news);
    return NewsDto.fromEntity(savedNews);
  }

  @Transactional
  public NewsDto updateNews(Long id, News newsDetails) {
    News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("기사를 찾을 수 없습니다. ID=" + id));

    news.setTitle(newsDetails.getTitle());
    news.setFullText(newsDetails.getFullText());
    news.setCategory(newsDetails.getCategory());
    news.setSourceLink(newsDetails.getSourceLink());
    news.setPublishedAt(newsDetails.getPublishedAt());
    news.setImage(newsDetails.getImage());
    news.setCreateAt(newsDetails.getCreateAt());

    News updatedNews = newsRepository.save(news);
    return NewsDto.fromEntity(updatedNews);
  }

  @Transactional
  public void deleteNews(Long id) {
    if (!newsRepository.existsById(id)) {
      throw new IllegalArgumentException("기사를 찾을 수 없습니다. ID=" + id);
    }
    newsRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public NewsWithScrabDto getNewsWithScrabStatus(Long id, String userEmail) {
    News news = newsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("기사를 찾을 수 없습니다. ID=" + id));

    boolean scrabbed = false;
    if (userEmail != null && !userEmail.isBlank()) {
      scrabbed = scrabRepository.existsById(new ScrabId(userEmail, id));
    }

    return NewsWithScrabDto.fromEntity(news, scrabbed);
  }

  public List<RecommendationNewsDto> getRecommendationNews(List<Long> idList) {
    List<RecommendationNewsDto> recommendationNewsDtoList = new ArrayList<>();
    for (Long l : idList) {
      recommendationNewsDtoList.add(new RecommendationNewsDto(
          newsRepository.findById(l).orElseThrow(() -> new IllegalArgumentException("기사를 찾을 수 없습니다. ID=" + l))));
    }
    return recommendationNewsDtoList;
  }
}
