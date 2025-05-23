package edu.goorm.news_service.domain.controller;

import edu.goorm.news_service.domain.dto.NewsDto;
import edu.goorm.news_service.domain.dto.RecommendationNewsDto;
import edu.goorm.news_service.domain.entity.News;
import edu.goorm.news_service.domain.service.NewsService;
import edu.goorm.news_service.global.logger.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

  private final NewsService newsService;

  public NewsController(NewsService newsService) {
    this.newsService = newsService;
  }

  @GetMapping
  public Page<NewsDto> getAllNews(Pageable pageable,
      @RequestHeader(value = "X-User-Email", required = false) String userEmail,
      HttpServletRequest request) {
    long start = System.currentTimeMillis();

    Page<NewsDto> result = newsService.getAllNews(pageable);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("GET_ALL_NEWS", "/api/news", "GET", userEmail, request, HttpStatus.OK.value(), end - start);
    return result;
  }

  @GetMapping("/{newsId}")
  public ResponseEntity<NewsDto> getNewsWithScrabCheck(
      @PathVariable Long newsId,
      @RequestHeader(value = "X-User-Email", required = false) String userEmail,
      HttpServletRequest request) {

    long start = System.currentTimeMillis();

    NewsDto newsDto = newsService.getNewsById(newsId);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("GET_NEWS", "/api/news/" + newsId, "GET", userEmail, request, HttpStatus.OK.value(),
        end - start);

    return ResponseEntity.ok(newsDto);
  }

  @GetMapping("/search")
  public Page<NewsDto> searchNews(@RequestParam String keyword, Pageable pageable,
      @RequestHeader(value = "X-User-Email", required = false) String userEmail,
      HttpServletRequest request) {

    long start = System.currentTimeMillis();

    Page<NewsDto> result = newsService.searchNews(keyword, pageable);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("SEARCH_NEWS", "/api/news/search", "GET", keyword, request, HttpStatus.OK.value(),
        end - start);

    return result;
  }

  @GetMapping("/category/{category}")
  public Page<NewsDto> getNewsByCategory(
      @PathVariable String category,
      Pageable pageable,
      @RequestHeader(value = "X-User-Email", required = false) String userEmail,
      HttpServletRequest request) {

    long start = System.currentTimeMillis();

    Page<NewsDto> result = newsService.getNewsByCategory(category, pageable);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("GET_NEWS_BY_CATEGORY", "/api/news/category/" + category, "GET", userEmail, request,
        HttpStatus.OK.value(), end - start);

    return result;
  }

  @PostMapping
  public NewsDto createNews(@RequestBody News news, HttpServletRequest request) {
    long start = System.currentTimeMillis();

    NewsDto created = newsService.createNews(news);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("CREATE_NEWS", "/api/news", "POST", news.getTitle(), request, HttpStatus.CREATED.value(),
        end - start);

    return created;
  }

  @PutMapping("/{id}")
  public NewsDto updateNews(@PathVariable Long id, @RequestBody News news, HttpServletRequest request) {
    long start = System.currentTimeMillis();

    NewsDto updated = newsService.updateNews(id, news);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("UPDATE_NEWS", "/api/news/" + id, "PUT", news.getTitle(), request, HttpStatus.OK.value(),
        end - start);

    return updated;
  }

  @DeleteMapping("/{id}")
  public void deleteNews(@PathVariable Long id, HttpServletRequest request) {
    long start = System.currentTimeMillis();

    newsService.deleteNews(id);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("DELETE_NEWS", "/api/news/" + id, "DELETE", String.valueOf(id), request,
        HttpStatus.OK.value(), end - start);
  }

  @PostMapping("/internal/find-news-by-NewsId")
  public ResponseEntity<List<RecommendationNewsDto>> getRecommendationNews(@RequestBody List<Long> idList,
      HttpServletRequest request) {
    long start = System.currentTimeMillis();

    List<RecommendationNewsDto> result = newsService.getRecommendationNews(idList);

    long end = System.currentTimeMillis();
    CustomLogger.logRequest("FIND_NEWS_BY_IDS", "/api/news/internal/find-news-by-NewsId", "POST", idList.toString(),
        request, HttpStatus.OK.value(), end - start);

    return ResponseEntity.ok(result);
  }
}
