package edu.goorm.news_service.service;

import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.exception.NewsNotFoundException;
import edu.goorm.news_service.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 기사 관련 비즈니스 로직을 처리하는 서비스 클래스
 * 기사의 CRUD 작업과 검색 기능을 구현
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;

    /**
     * 모든 기사를 페이지네이션하여 조회
     * @param pageable 페이지네이션 정보
     * @return 기사 목록 페이지
     */
    public Page<NewsDto> getAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(NewsDto::fromEntity);
    }

    /**
     * ID로 특정 기사 조회
     * @param id 조회할 기사의 ID
     * @return 조회된 기사 정보
     * @throws NewsNotFoundException 기사가 존재하지 않는 경우
     */
    public NewsDto getNewsById(Long id) {
        return newsRepository.findById(id)
                .map(NewsDto::fromEntity)
                .orElseThrow(() -> new NewsNotFoundException("뉴스를 찾을 수 없습니다."));
    }

    /**
     * 제목에 키워드가 포함된 기사 검색
     * @param keyword 검색할 키워드
     * @param pageable 페이지네이션 정보
     * @return 검색된 기사 목록 페이지
     */
    public Page<NewsDto> searchNews(String keyword, Pageable pageable) {
        return newsRepository.findByTitleContainingOrFullTextContaining(keyword, keyword, pageable)
                .map(NewsDto::fromEntity);
    }

    /**
     * 특정 카테고리의 기사 조회
     * @param category 조회할 카테고리
     * @param pageable 페이지네이션 정보
     * @return 카테고리에 해당하는 기사 목록 페이지
     */
    public Page<NewsDto> getNewsByCategory(String category, Pageable pageable) {
        return newsRepository.findByCategory(category, pageable)
                .map(NewsDto::fromEntity);
    }

    /**
     * 새로운 기사 생성
     * @param article 생성할 기사 정보
     * @return 생성된 기사 정보
     */
    public NewsDto createNews(News news) {
        News savedNews = newsRepository.save(news);
        return NewsDto.fromEntity(savedNews);
    }

    /**
     * 기존 기사 수정
     * @param id 수정할 기사의 ID
     * @param articleDetails 수정할 기사 정보
     * @return 수정된 기사 정보
     * @throws ArticleNotFoundException 기사가 존재하지 않는 경우
     */
    public NewsDto updateNews(Long id, News newsDetails) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("기사를 찾을 수 없습니다."));
        
        news.setTitle(newsDetails.getTitle());
        news.setFullText(newsDetails.getFullText());
        news.setSummary(newsDetails.getSummary());
        news.setCategory(newsDetails.getCategory());
        news.setSource(newsDetails.getSource());
        news.setPublishedAt(newsDetails.getPublishedAt());
        news.setImageLink(newsDetails.getImageLink());
        News updatedNews = newsRepository.save(news);
        return NewsDto.fromEntity(updatedNews);
    }

    /**
     * 기사 삭제
     * @param id 삭제할 기사의 ID
     * @throws ArticleNotFoundException 기사가 존재하지 않는 경우
     */
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new NewsNotFoundException("기사를 찾을 수 없습니다.");
        }
        newsRepository.deleteById(id);
    }
}