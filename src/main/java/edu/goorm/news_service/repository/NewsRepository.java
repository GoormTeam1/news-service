package edu.goorm.news_service.repository;

import edu.goorm.news_service.entity.News;
import jakarta.persistence.Table;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Table(name = "News")
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findByTitleContainingOrFullTextContaining(String title, String fullText, Pageable pageable);
    Page<News> findByCategory(String category, Pageable pageable);
} 
