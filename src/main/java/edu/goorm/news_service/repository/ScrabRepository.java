package edu.goorm.news_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.goorm.news_service.entity.Scrab;
import edu.goorm.news_service.entity.ScrabId;

public interface ScrabRepository extends JpaRepository<Scrab, ScrabId> {

    // ✅ 페이지네이션 적용
    Page<Scrab> findByIdUserId(Long userId, Pageable pageable);

    // 필요 시 상태 필터까지 포함한 버전도 가능
    Page<Scrab> findByIdUserIdAndStatus(Long userId, String status, Pageable pageable);
}
