package edu.goorm.news_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import edu.goorm.news_service.repository.ScrabRepository;
import edu.goorm.news_service.entity.Scrab;
import edu.goorm.news_service.entity.ScrabId;
import edu.goorm.news_service.dto.ScrabDto;


@Service
@RequiredArgsConstructor
public class ScrabService {

    private final ScrabRepository scrabRepository;

    public Page<ScrabDto> getUserScrabs(String userEmail, Pageable pageable) {
        Page<Scrab> scrabPage = scrabRepository.findByIdUserEmail(userEmail, pageable);
        List<ScrabDto> dtoList = scrabPage.getContent().stream()
                .map(scrab -> new ScrabDto(
                        scrab.getId().getUserEmail(),
                        scrab.getId().getNewsId(),
                        scrab.getStatus()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, scrabPage.getTotalElements());
    }

    public void addScrab(String userEmail, Long newsId, String status) {
        ScrabId id = new ScrabId(userEmail, newsId);
        if (!scrabRepository.existsById(id)) {
            Scrab scrab = new Scrab(userEmail, newsId, status);
            scrabRepository.save(scrab);
        }
    }

    public void deleteScrab(String userEmail, Long newsId) {
        scrabRepository.deleteById(new ScrabId(userEmail, newsId));
    }

    public void updateScrabStatus(String userEmail, Long newsId, String newStatus) {
        ScrabId id = new ScrabId(userEmail, newsId);
        scrabRepository.findById(id).ifPresent(scrab -> {
            scrab.setStatus(newStatus);
            scrabRepository.save(scrab);
        });
    }
}

