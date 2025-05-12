package edu.goorm.news_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.goorm.news_service.entity.Summary;
import java.util.List;
import java.util.Optional;

import edu.goorm.news_service.dto.*;

/**
 * Summary 엔티티를 관리하는 리포지토리 인터페이스
 */
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    
    /**
     * 주어진 기사 ID에 해당하는 요약 정보를 조회하는 메서드
     * @param newsId 기사 ID
     * @return 요약 정보 목록
     */
    List<Summary> findByNewsId(Long newsId);

    /**
     * 주어진 기사 ID와 레벨에 해당하는 요약 정보를 조회하는 메서드
     * @param newsId 기사 ID
     * @param level 레벨
     * @return 요약 정보
     */
    Summary findByNewsIdAndLevel(Long newsId, String level);

    Summary findBySummaryId(@Param("summaryId") Long summaryId);

}
