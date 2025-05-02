package edu.goorm.news_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ScrabId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "news_id")
    private Long newsId;

    public ScrabId() {}

    public ScrabId(Long userId, Long newsId) {
        this.userId = userId;
        this.newsId = newsId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScrabId)) return false;
        ScrabId scrabId = (ScrabId) o;
        return Objects.equals(userId, scrabId.userId) &&
               Objects.equals(newsId, scrabId.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, newsId);
    }
}
