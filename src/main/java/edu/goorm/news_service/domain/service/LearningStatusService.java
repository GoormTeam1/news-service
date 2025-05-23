package edu.goorm.news_service.domain.service;

import edu.goorm.news_service.domain.client.QuizClient;
import edu.goorm.news_service.domain.repository.SummaryRepository;
import edu.goorm.news_service.domain.dto.WrongQuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LearningStatusService {

    private final SummaryRepository summaryRepository;
    private final QuizClient quizClient;

    public String getLearningStatusForNews(String userEmail, Long newsId) {

        // 1. 뉴스에 연결된 summaryId 목록 조회
        List<Long> summaryIds = summaryRepository.findIdsByNewsId(newsId);

        // 2. 퀴즈 서비스로부터 전체 wrong_quiz 리스트 조회
        List<WrongQuizDto> allWrongQuizzes = quizClient.getWrongQuizzes(userEmail);

        // 3. 뉴스 관련 summaryId만 필터링
        List<String> statusList = allWrongQuizzes.stream()
                .filter(dto -> summaryIds.contains(dto.getSummaryId()))
                .map(WrongQuizDto::getStatus)
                .toList();

        // 4. 상태 판별
        if (statusList.size() == summaryIds.size() && statusList.stream().allMatch(s -> s.equals("completed"))) {
            return "completed";
        }
        if (statusList.contains("completed")) {
            return "learning";
        }
        if (statusList.contains("learning")) {
            return "learning";
        }

        return "not_learning";
    }
}
