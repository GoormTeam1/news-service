package edu.goorm.news_service.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.goorm.news_service.domain.entity.Scrab;
import edu.goorm.news_service.domain.entity.ScrabId;

public interface ScrabRepository extends JpaRepository<Scrab, ScrabId> {

    Page<Scrab> findByIdUserEmail(String userEmail, Pageable pageable);
}
