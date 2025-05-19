package edu.goorm.news_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import edu.goorm.news_service.dto.ScrabDto;
import edu.goorm.news_service.logger.CustomLogger;
import edu.goorm.news_service.service.ScrabService;

@RestController
@RequestMapping("/api/scrabs")
@RequiredArgsConstructor
public class ScrabController {

    private final ScrabService scrabService;

    // 이메일 기반으로 스크랩 조회
    @GetMapping("/{userEmail}")
    public Page<ScrabDto> getScrabs(
            @PathVariable String userEmail,
            Pageable pageable,
            HttpServletRequest request
    ) {
        CustomLogger.logRequest(
                "GET_SCRABS",
                "/api/scrabs/" + userEmail,
                "GET",
                userEmail,
                null,
                request
        );
        return scrabService.getUserScrabs(userEmail, pageable);
    }

    // 이메일 기반으로 스크랩 추가
    @PostMapping
    public void addScrab(@RequestBody ScrabDto dto, HttpServletRequest request) {
        CustomLogger.logRequest(
                "ADD_SCRAB",
                "/api/scrabs",
                "POST",
                dto.getUserEmail(),
                dto.toString(),
                request
        );
        scrabService.addScrab(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
    }

    // 이메일 기반으로 스크랩 삭제
    @DeleteMapping
    public void deleteScrab(
            @RequestParam String userEmail,
            @RequestParam Long newsId,
            HttpServletRequest request
    ) {
        CustomLogger.logRequest(
                "DELETE_SCRAB",
                "/api/scrabs",
                "DELETE",
                userEmail,
                "newsId: " + newsId,
                request
        );
        scrabService.deleteScrab(userEmail, newsId);
    }

    // 이메일 기반으로 스크랩 상태 변경
    @PutMapping
    public void updateScrabStatus(@RequestBody ScrabDto dto, HttpServletRequest request) {
        CustomLogger.logRequest(
                "UPDATE_SCRAB_STATUS",
                "/api/scrabs",
                "PUT",
                dto.getUserEmail(),
                dto.toString(),
                request
        );
        scrabService.updateScrabStatus(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
    }
}
