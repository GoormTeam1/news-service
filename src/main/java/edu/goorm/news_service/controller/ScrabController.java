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

    @GetMapping("/{userId}")
    public Page<ScrabDto> getScrabs(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return scrabService.getUserScrabs(userId, pageable);
    }

    @PostMapping
    public void addScrab(@RequestBody ScrabDto dto) {
        scrabService.addScrab(dto.getUserId(), dto.getNewsId(), dto.getStatus());
    }

    @DeleteMapping
    public void deleteScrab(@RequestParam Long userId,
                            @RequestParam Long newsId) {
        scrabService.deleteScrab(userId, newsId);
    }

    @PutMapping
    public void updateScrabStatus(@RequestBody ScrabDto dto) {
        scrabService.updateScrabStatus(dto.getUserId(), dto.getNewsId(), dto.getStatus());
    }
}
