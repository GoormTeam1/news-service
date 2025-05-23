package edu.goorm.news_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

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

    @Column(name = "source_link")
    private String sourceLink;

    @Column
    private String category;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    private String image;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDate createAt;

}