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

    public Scrab(Long userId, Long newsId, String status) {
        this.id = new ScrabId(userId, newsId);
        this.status = status;
    }
}
