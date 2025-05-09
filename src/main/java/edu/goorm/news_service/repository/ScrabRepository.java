package edu.goorm.news_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.goorm.news_service.entity.Scrab;
import edu.goorm.news_service.entity.ScrabId;

public interface ScrabRepository extends JpaRepository<Scrab, ScrabId> {

    Page<Scrab> findByIdUserEmail(String userEmail, Pageable pageable);
}
