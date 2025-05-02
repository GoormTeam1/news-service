package edu.goorm.news_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

/**
 * 기사 정보를 저장하는 엔티티 클래스
 */
@Entity
@Getter
@Setter
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class News {

    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "full_text", columnDefinition = "TEXT")
    private String fullText;

    @Column
    private String summary;

    @Column
    private String source;

    @Column
    private String category;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "image_link")
    private String imageLink;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}