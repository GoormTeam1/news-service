package edu.goorm.news_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ScrabId implements Serializable {

    @Column(name = "user_email", nullable = false, length = 255)
    private String userEmail;

    @Column(name = "news_id", nullable = false)
    private Long newsId;

    public ScrabId() {}

    public ScrabId(String userEmail, Long newsId) {
        this.userEmail = userEmail;
        this.newsId = newsId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScrabId)) return false;
        ScrabId scrabId = (ScrabId) o;
        return Objects.equals(userEmail, scrabId.userEmail) &&
               Objects.equals(newsId, scrabId.newsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, newsId);
    }
}
