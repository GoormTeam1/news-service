package edu.goorm.news_service.controller;

import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.dto.NewsWithScrabDto;
import edu.goorm.news_service.dto.RecommendationNewsDto;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 기사 관련 API를 처리하는 컨트롤러
 * 기사의 CRUD 작업과 검색 기능을 제공
 */

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    
    public NewsController(NewsService newsService) {
        this.newsService = newsService;   
    }

    @GetMapping
    public Page<NewsDto> getAllNews(Pageable pageable) {
        return newsService.getAllNews(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsWithScrabDto> getNewsWithScrabCheck(
            @PathVariable Long id,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail) {
        NewsWithScrabDto news = newsService.getNewsWithScrabStatus(id, userEmail);
        return ResponseEntity.ok(news);
    }

    

    @GetMapping("/search")
    public Page<NewsDto> searchNews(@RequestParam String keyword, Pageable pageable) {
        return newsService.searchNews(keyword, pageable);
    }

    @GetMapping("/category/{category}")
    public Page<NewsDto> getNewsByCategory(@PathVariable String category, Pageable pageable) {
        return newsService.getNewsByCategory(category, pageable);
    }

    @PostMapping
    public NewsDto createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    @PutMapping("/{id}")
    public NewsDto updateNews(@PathVariable Long id, @RequestBody News news) {
        return newsService.updateNews(id, news);
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }

    @PostMapping("/internal/find-news-by-NewsId")
    public ResponseEntity<List<RecommendationNewsDto>> getRecommendationNews(@RequestBody List<Long> idList) {
        return ResponseEntity.ok(newsService.getRecommendationNews(idList));
    }

   
}
