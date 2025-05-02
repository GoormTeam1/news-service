package edu.goorm.news_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import edu.goorm.news_service.service.NewsService;
import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.entity.News;
    
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

    /**
     * 모든 기사를 페이지네이션하여 조회
     * @param pageable 페이지네이션 정보 (페이지 번호, 크기, 정렬 등)
     * @return 기사 목록 페이지
     */
    @GetMapping
    public Page<NewsDto> getAllNews(Pageable pageable) {
        return newsService.getAllNews(pageable);
    }

    /**
     * ID로 특정 기사 조회
     * @param id 조회할 기사의 ID
     * @return 조회된 기사 정보
     */
    @GetMapping("/{id}")
    public NewsDto getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    /**
     * 제목에 키워드가 포함된 기사 검색
     * @param keyword 검색할 키워드
     * @param pageable 페이지네이션 정보
     * @return 검색된 기사 목록 페이지
     */
    @GetMapping("/search")
    public Page<NewsDto> searchNews(@RequestParam String keyword, Pageable pageable) {
        return newsService.searchNews(keyword, pageable);
    }

    /**
     * 특정 카테고리의 기사 조회
     * @param category 조회할 카테고리
     * @param pageable 페이지네이션 정보
     * @return 카테고리에 해당하는 기사 목록 페이지
     */
    @GetMapping("/category/{category}")
    public Page<NewsDto> getNewsByCategory(@PathVariable String category, Pageable pageable) {
        return newsService.getNewsByCategory(category, pageable);
    }

    /**
     * 새로운 기사 생성
     * @param article 생성할 기사 정보
     * @return 생성된 기사 정보
     */
    @PostMapping
    public NewsDto createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    /**
     * 기존 기사 수정
     * @param id 수정할 기사의 ID
     * @param news 수정할 기사 정보
     * @return 수정된 기사 정보
     */ 
    @PutMapping("/{id}")
    public NewsDto updateNews(@PathVariable Long id, @RequestBody News news) {
        return newsService.updateNews(id, news);
    }

    /**
     * 기사 삭제
     * @param id 삭제할 기사의 ID
     */
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}