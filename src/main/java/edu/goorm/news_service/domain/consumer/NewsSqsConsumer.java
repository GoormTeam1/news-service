package edu.goorm.news_service.domain.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.goorm.news_service.domain.dto.NewsDto;
import edu.goorm.news_service.domain.service.NewsBulkInsertBuffer;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsSqsConsumer {

    private final ObjectMapper objectMapper;
    private final NewsBulkInsertBuffer bulkInsertBuffer;

    /**
     * SQS 큐 'news-queue'로부터 메시지를 수신합니다.
     */
    @SqsListener("news-queue")  // 반드시 실제 SQS 큐 이름과 일치시킬 것
    public void consume(@Payload String message) {
        try {
            NewsDto dto = objectMapper.readValue(message, NewsDto.class);
            bulkInsertBuffer.add(dto);  // 버퍼에 추가 (100개 이상시 자동 저장)
        } catch (Exception e) {
            log.error("❌ SQS message parse error\nPayload: {}", message, e);
        }
    }
}
