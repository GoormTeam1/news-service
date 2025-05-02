package edu.goorm.news_service.service;

import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.exception.NewsNotFoundException;
import edu.goorm.news_service.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    @Test
    @DisplayName("전체 뉴스 조회 - 성공")
    void getAllNews_Success() {
        // given
        News news1 = createSampleNews(1L, "News 1", "Content 1", "Source 1", "Tech");
        News news2 = createSampleNews(2L, "News 2", "Content 2", "Source 2", "Sports");
        List<News> newsList = Arrays.asList(news1, news2);
        Page<News> newsPage = new PageImpl<>(newsList);

        when(newsRepository.findAll(any(Pageable.class))).thenReturn(newsPage);

        // when
        Page<NewsDto> result = newsService.getAllNews(PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("News 1");
        assertThat(result.getContent().get(1).getTitle()).isEqualTo("News 2");
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 성공")
    void getNewsById_Success() {
        // given
        News news = createSampleNews(1L, "Test News", "Test Content", "Test Source", "Tech");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        // when
        NewsDto result = newsService.getNewsById(1L);

        // then
        assertThat(result.getTitle()).isEqualTo("Test News");
        assertThat(result.getContent()).isEqualTo("Test Content");
        assertThat(result.getCategory()).isEqualTo("Tech");
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 실패 (존재하지 않는 뉴스)")
    void getNewsById_NotFound() {
        // given
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> newsService.getNewsById(1L))
                .isInstanceOf(NewsNotFoundException.class)
                .hasMessage("뉴스를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("뉴스 검색 - 성공")
    void searchNews_Success() {
        // given
        News news = createSampleNews(1L, "Test News", "Test Content", "Test Source", "Tech");
        Page<News> newsPage = new PageImpl<>(List.of(news));

        when(newsRepository.findByTitleContainingOrFull_textContaining(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(newsPage);

        // when
        Page<NewsDto> result = newsService.searchNews("Test", PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Test News");
    }

    @Test
    @DisplayName("카테고리별 뉴스 조회 - 성공")
    void getNewsByCategory_Success() {
        // given
        News news = createSampleNews(1L, "Tech News", "Tech Content", "Tech Source", "Tech");
        Page<News> newsPage = new PageImpl<>(List.of(news));

        when(newsRepository.findByCategory(anyString(), any(Pageable.class))).thenReturn(newsPage);

        // when
        Page<NewsDto> result = newsService.getNewsByCategory("Tech", PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCategory()).isEqualTo("Tech");
    }

    @Test
    @DisplayName("뉴스 생성 - 성공")
    void createNews_Success() {
        // given
        News news = createSampleNews(null, "New News", "New Content", "New Source", "Tech");
        News savedNews = createSampleNews(1L, "New News", "New Content", "New Source", "Tech");

        when(newsRepository.save(any(News.class))).thenReturn(savedNews);

        // when
        NewsDto result = newsService.createNews(news);

        // then
        assertThat(result.getTitle()).isEqualTo("New News");
        assertThat(result.getContent()).isEqualTo("New Content");
        assertThat(result.getCategory()).isEqualTo("Tech");
    }

    @Test
    @DisplayName("뉴스 수정 - 성공")
    void updateNews_Success() {
        // given
        News existingNews = createSampleNews(1L, "Old News", "Old Content", "Old Source", "Tech");
        News updateInfo = createSampleNews(null, "Updated News", "Updated Content", "Updated Source", "Tech");
        News updatedNews = createSampleNews(1L, "Updated News", "Updated Content", "Updated Source", "Tech");

        when(newsRepository.findById(1L)).thenReturn(Optional.of(existingNews));
        when(newsRepository.save(any(News.class))).thenReturn(updatedNews);

        // when
        NewsDto result = newsService.updateNews(1L, updateInfo);

        // then
        assertThat(result.getTitle()).isEqualTo("Updated News");
        assertThat(result.getContent()).isEqualTo("Updated Content");
        assertThat(result.getCategory()).isEqualTo("Tech");
    }

    @Test
    @DisplayName("뉴스 수정 - 실패 (존재하지 않는 뉴스)")
    void updateNews_NotFound() {
        // given
        News updateInfo = createSampleNews(null, "Updated News", "Updated Content", "Updated Source", "Tech");
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> newsService.updateNews(1L, updateInfo))
                .isInstanceOf(NewsNotFoundException.class)
                .hasMessage("뉴스를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("뉴스 삭제 - 성공")
    void deleteNews_Success() {
        // given
        News news = createSampleNews(1L, "Test News", "Test Content", "Test Source", "Tech");
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        // when
        newsService.deleteNews(1L);

        // then
        verify(newsRepository, times(1)).delete(news);
    }

    @Test
    @DisplayName("뉴스 삭제 - 실패 (존재하지 않는 뉴스)")
    void deleteNews_NotFound() {
        // given
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> newsService.deleteNews(1L))
                .isInstanceOf(NewsNotFoundException.class)
                .hasMessage("뉴스를 찾을 수 없습니다.");
    }

    private News createSampleNews(Long id, String title, String content, String source, String category) {
        LocalDateTime now = LocalDateTime.now();
        return News.builder()
                .id(id)
                .title(title)
                .full_text(content)
                .source(source)
                .category(category)
                .publishedDate(now)
                .summary("Test Summary")
                .image("https://example.com/image.jpg")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
} 