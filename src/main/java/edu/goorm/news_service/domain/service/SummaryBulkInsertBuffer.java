package edu.goorm.news_service.domain.service;

import edu.goorm.news_service.domain.dto.SummaryDto;
import edu.goorm.news_service.domain.repository.SummaryRepository;
import edu.goorm.news_service.domain.entity.Summary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummaryBulkInsertBuffer {

    private final List<Summary> buffer = Collections.synchronizedList(new ArrayList<>());
    private final SummaryRepository summaryRepository;
    private static final int BATCH_SIZE = 100;
    private int messageCount = 0;

    public synchronized void add(SummaryDto dto) {
        buffer.add(Summary.builder()
                .newsId(dto.getNewsId())
                .level(dto.getLevel())
                .summary(dto.getSummary())
                .build());

        messageCount++;
        log.info("Added summary item to buffer: {} (count={})", dto.getSummary(), messageCount);

        if (messageCount >= BATCH_SIZE) {
            flush();
        }
    }

    @Scheduled(fixedRate = 5000)
    public void periodicFlush() {
        if (!buffer.isEmpty()) flush();
    }

    @Transactional
    public synchronized void flush() {
        if (buffer.isEmpty()) return;
        List<Summary> toSave = new ArrayList<>(buffer);
        buffer.clear();
        messageCount = 0;
        summaryRepository.saveAll(toSave);
        log.info("Flushed {} summary items to DB", toSave.size());
    }
}
