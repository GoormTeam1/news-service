package edu.goorm.news_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "scrab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Scrab {

    @EmbeddedId
    private ScrabId id;

    @Column(name = "status", length = 20)
    private String status;

    // 변경된 생성자: userId → userEmail
    public Scrab(String userEmail, Long newsId, String status) {
        this.id = new ScrabId(userEmail, newsId);
        this.status = status;
    }
}
