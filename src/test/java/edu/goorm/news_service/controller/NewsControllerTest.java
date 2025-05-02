package edu.goorm.news_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.exception.NewsNotFoundException;
import edu.goorm.news_service.service.NewsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("전체 뉴스 조회 - 성공")
    void getAllNews_Success() throws Exception {
        // given
        NewsDto dto = createSampleNewsDto(1L, "Test Title", "Test Content", "Test Summary", "Test Category");
        Page<NewsDto> page = new PageImpl<>(List.of(dto));

        when(newsService.getAllNews(any(Pageable.class))).thenReturn(page);

        // when & then
        mockMvc.perform(get("/api/news")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "id,desc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("Test Title"))
            .andExpect(jsonPath("$.content[0].category").value("Test Category"))
            .andExpect(jsonPath("$.totalElements").value(1));

        verify(newsService, times(1)).getAllNews(any(Pageable.class));
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 성공")
    void getNewsById_Success() throws Exception {
        // given
        NewsDto dto = createSampleNewsDto(1L, "Test Title", "Test Content", "Test Summary", "Test Category");
        when(newsService.getNewsById(1L)).thenReturn(dto);

        // when & then
        mockMvc.perform(get("/api/news/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.category").value("Test Category"));

        verify(newsService, times(1)).getNewsById(1L);
    }

    @Test
    @DisplayName("단일 뉴스 조회 - 실패 (존재하지 않는 뉴스)")
    void getNewsById_NotFound() throws Exception {
        // given
        when(newsService.getNewsById(1L))
            .thenThrow(new NewsNotFoundException("뉴스를 찾을 수 없습니다."));

        // when & then
        mockMvc.perform(get("/api/news/1"))
            .andExpect(status().isNotFound());

        verify(newsService, times(1)).getNewsById(1L);
    }

    @Test
    @DisplayName("뉴스 검색 - 성공")
    void searchNews_Success() throws Exception {
        // given
        NewsDto dto = createSampleNewsDto(1L, "Test Title", "Test Content", "Test Summary", "Test Category");
        Page<NewsDto> page = new PageImpl<>(List.of(dto));

        when(newsService.searchNews(anyString(), any(Pageable.class))).thenReturn(page);

        // when & then
        mockMvc.perform(get("/api/news/search")
                .param("keyword", "Test")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title").value("Test Title"))
            .andExpect(jsonPath("$.totalElements").value(1));

        verify(newsService, times(1)).searchNews(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("카테고리별 뉴스 조회 - 성공")
    void getNewsByCategory_Success() throws Exception {
        // given
        NewsDto dto = createSampleNewsDto(1L, "Test Title", "Test Content", "Test Summary", "Tech");
        Page<NewsDto> page = new PageImpl<>(List.of(dto));

        when(newsService.getNewsByCategory(anyString(), any(Pageable.class))).thenReturn(page);

        // when & then
        mockMvc.perform(get("/api/news/category/Tech")
                .param("page", "0")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].category").value("Tech"))
            .andExpect(jsonPath("$.totalElements").value(1));

        verify(newsService, times(1)).getNewsByCategory(anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("뉴스 생성 - 성공")
    void createNews_Success() throws Exception {
        // given
        News news = createSampleNews(null, "New Title", "New Content", "New Source", "Tech");
        NewsDto savedDto = createSampleNewsDto(1L, "New Title", "New Content", "New Summary", "Tech");

        when(newsService.createNews(any(News.class))).thenReturn(savedDto);

        // when & then
        mockMvc.perform(post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(news)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("New Title"))
            .andExpect(jsonPath("$.category").value("Tech"));

        verify(newsService, times(1)).createNews(any(News.class));
    }

    @Test
    @DisplayName("뉴스 수정 - 성공")
    void updateNews_Success() throws Exception {
        // given
        News updateInfo = createSampleNews(null, "Updated Title", "Updated Content", "Updated Source", "Tech");
        NewsDto updatedDto = createSampleNewsDto(1L, "Updated Title", "Updated Content", "Updated Summary", "Tech");

        when(newsService.updateNews(eq(1L), any(News.class))).thenReturn(updatedDto);

        // when & then
        mockMvc.perform(put("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInfo)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.category").value("Tech"));

        verify(newsService, times(1)).updateNews(eq(1L), any(News.class));
    }

    @Test
    @DisplayName("뉴스 수정 - 실패 (존재하지 않는 뉴스)")
    void updateNews_NotFound() throws Exception {
        // given
        News updateInfo = createSampleNews(null, "Updated Title", "Updated Content", "Updated Source", "Tech");
        when(newsService.updateNews(eq(1L), any(News.class)))
            .thenThrow(new NewsNotFoundException("뉴스를 찾을 수 없습니다."));

        // when & then
        mockMvc.perform(put("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateInfo)))
            .andExpect(status().isNotFound());

        verify(newsService, times(1)).updateNews(eq(1L), any(News.class));
    }

    @Test
    @DisplayName("뉴스 삭제 - 성공")
    void deleteNews_Success() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/news/1"))
            .andExpect(status().isOk());

        verify(newsService, times(1)).deleteNews(1L);
    }

    private NewsDto createSampleNewsDto(Long id, String title, String content, String summary, String category) {
        LocalDateTime now = LocalDateTime.now();
        return NewsDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .summary(summary)
                .source("Test Source")
                .category(category)
                .publishedDate(now)
                .image("https://example.com/image.jpg")
                .createdAt(now)
                .updatedAt(now)
                .build();
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