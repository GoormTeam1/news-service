package edu.goorm.news_service.service;

import edu.goorm.news_service.dto.NewsDto;
import edu.goorm.news_service.entity.News;
import edu.goorm.news_service.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsBulkInsertBuffer {

    private final List<News> buffer = Collections.synchronizedList(new ArrayList<>());
    private final List<Acknowledgment> ackBuffer = Collections.synchronizedList(new ArrayList<>());
    private final NewsRepository newsRepository;
    private static final int BATCH_SIZE = 100;

    public void add(NewsDto dto, Acknowledgment ack) {
        buffer.add(News.builder()
                .title(dto.getTitle())
                .fullText(dto.getFullText())
                .sourceLink(dto.getSourceLink())
                .category(dto.getCategory())
                .publishedAt(dto.getPublishedAt())
                .image(dto.getImage())
                .createAt(dto.getCreateAt())
                .build());
        ackBuffer.add(ack);
        log.info("Added news item to buffer: {}", dto.getTitle());
        if (buffer.size() >= BATCH_SIZE) flush();
    }

    @Scheduled(fixedRate = 5000)
    public void periodicFlush() {
        if (!buffer.isEmpty()) flush();
    }

    @Transactional
    public synchronized void flush() {
        if (buffer.isEmpty()) return;
        List<News> toSave = new ArrayList<>(buffer);
        List<Acknowledgment> toAck = new ArrayList<>(ackBuffer);
        buffer.clear();
        ackBuffer.clear();

        newsRepository.saveAll(toSave);
        toAck.forEach(Acknowledgment::acknowledge);

        log.info("Flushed and committed {} news items to DB", toSave.size());
    }
} 