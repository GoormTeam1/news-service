package edu.goorm.news_service.domain.service;

import edu.goorm.news_service.domain.dto.NewsDto;
import edu.goorm.news_service.domain.entity.News;
import edu.goorm.news_service.domain.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsBulkInsertBuffer {

    private final List<News> buffer = Collections.synchronizedList(new ArrayList<>());
    private final NewsRepository newsRepository;
    private static final int BATCH_SIZE = 100;

    public synchronized void add(NewsDto dto) {
        buffer.add(News.builder()
                .title(dto.getTitle())
                .fullText(dto.getFullText())
                .sourceLink(dto.getSourceLink())
                .category(dto.getCategory())
                .publishedAt(dto.getPublishedAt())
                .image(dto.getImage())
                .createAt(LocalDate.now())
                .build());
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
        buffer.clear();

        newsRepository.saveAll(toSave);

        log.info("Flushed {} news items to DB", toSave.size());
    }
}
