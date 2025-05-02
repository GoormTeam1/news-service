package edu.goorm.news_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrabDto {
    
    private Long userId;
    private Long newsId;
    private String status;
}
