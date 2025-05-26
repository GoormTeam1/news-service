package edu.goorm.news_service.consumer;

import edu.goorm.news_service.service.NewsBulkInsertBuffer;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.news_service.dto.NewsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsKafkaConsumer {

    private final NewsBulkInsertBuffer bulkInsertBuffer;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "news_topic", groupId = "news-group")
    public void consume(String message, Acknowledgment ack) {
        try {
            NewsDto dto = objectMapper.readValue(message, NewsDto.class);
            bulkInsertBuffer.add(dto, ack);
        } catch (Exception e) {
            log.error("Kafka message parse error: {}", message, e);
            ack.acknowledge(); // skip malformed messages to prevent retry loop
        }
    }
}
