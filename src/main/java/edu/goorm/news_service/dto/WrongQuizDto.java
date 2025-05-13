package edu.goorm.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WrongQuizDto {
    private String userEmail;
    private Long summaryId;
    private String status;

    // Constructor to fix the issue
    public WrongQuizDto(String userEmail, Long summaryId, String status) {
        this.status = status;
        this.userEmail = userEmail;
        this.summaryId = summaryId;
    }

    // Getters and setters (if not already present)
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}