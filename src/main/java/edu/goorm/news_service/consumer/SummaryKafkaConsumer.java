package edu.goorm.news_service.consumer;
import edu.goorm.news_service.domain.service.SummaryBulkInsertBuffer;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.news_service.domain.dto.SummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class SummaryKafkaConsumer {

    private final SummaryBulkInsertBuffer bulkInsertBuffer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "summary", groupId = "news-group")
    public void consume(String message) {
        try {
            SummaryDto dto = objectMapper.readValue(message, SummaryDto.class);
            bulkInsertBuffer.add(dto);
        } catch (Exception e) {
            log.error("Kafka message parse error: {}", message, e);
        }
    }
}
