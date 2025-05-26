package edu.goorm.news_service.consumer;

import edu.goorm.news_service.domain.service.NewsBulkInsertBuffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.news_service.domain.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsKafkaConsumer {

    private final NewsBulkInsertBuffer bulkInsertBuffer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "news_topic", groupId = "news-group")
    public void consume(String message) {
        try {
            NewsDto dto = objectMapper.readValue(message, NewsDto.class);
            bulkInsertBuffer.add(dto);  // Acknowledgment 제거
        } catch (Exception e) {
            log.error("Kafka message parse error: {}", message, e);
            // acknowledgment 처리 제거, auto-commit이므로 메시지 커밋 처리 Kafka가 자동 수행
        }
    }
}
