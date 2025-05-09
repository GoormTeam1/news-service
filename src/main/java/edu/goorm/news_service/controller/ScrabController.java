package edu.goorm.news_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import edu.goorm.news_service.service.ScrabService;
import edu.goorm.news_service.dto.ScrabDto;

@RestController
@RequestMapping("/api/scrabs")
@RequiredArgsConstructor
public class ScrabController {

    private final ScrabService scrabService;

    // 이메일 기반으로 스크랩 조회
    @GetMapping("/{userEmail}")
    public Page<ScrabDto> getScrabs(
            @PathVariable String userEmail,
            Pageable pageable
    ) {
        return scrabService.getUserScrabs(userEmail, pageable);
    }

    // 이메일 기반으로 스크랩 추가
    @PostMapping
    public void addScrab(@RequestBody ScrabDto dto) {
        scrabService.addScrab(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
    }

    // 이메일 기반으로 스크랩 삭제
    @DeleteMapping
    public void deleteScrab(@RequestParam String userEmail,
                            @RequestParam Long newsId) {
        scrabService.deleteScrab(userEmail, newsId);
    }

    // 이메일 기반으로 스크랩 상태 변경
    @PutMapping
    public void updateScrabStatus(@RequestBody ScrabDto dto) {
        scrabService.updateScrabStatus(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
    }
}
