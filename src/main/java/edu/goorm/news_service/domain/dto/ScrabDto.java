package edu.goorm.news_service.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrabDto {
    private String userEmail;
    private Long newsId;
    private String status;
}
