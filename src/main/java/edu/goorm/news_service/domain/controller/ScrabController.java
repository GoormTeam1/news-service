package edu.goorm.news_service.domain.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.goorm.news_service.domain.dto.ScrabDto;
import edu.goorm.news_service.global.logger.CustomLogger;
import edu.goorm.news_service.domain.service.ScrabService;

@RestController
@RequestMapping("/api/scrabs")
@RequiredArgsConstructor
public class ScrabController {

    private final ScrabService scrabService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<Page<ScrabDto>> getScrabs(
        @PathVariable String userEmail,
        Pageable pageable,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();
        Page<ScrabDto> result = scrabService.getUserScrabs(userEmail, pageable);
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "GET_SCRABS",
            "/api/scrabs/" + userEmail,
            "GET",
            userEmail,
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> addScrab(@RequestBody ScrabDto dto, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        scrabService.addScrab(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "ADD_SCRAB",
            "/api/scrabs",
            "POST",
            dto.getUserEmail(),
            request,
            HttpStatus.CREATED.value(),
            end - start
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteScrab(
        @RequestParam String userEmail,
        @RequestParam Long newsId,
        HttpServletRequest request) {

        long start = System.currentTimeMillis();
        scrabService.deleteScrab(userEmail, newsId);
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "DELETE_SCRAB",
            "/api/scrabs",
            "DELETE",
            userEmail,
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateScrabStatus(@RequestBody ScrabDto dto, HttpServletRequest request) {
        long start = System.currentTimeMillis();
        scrabService.updateScrabStatus(dto.getUserEmail(), dto.getNewsId(), dto.getStatus());
        long end = System.currentTimeMillis();

        CustomLogger.logRequest(
            "UPDATE_SCRAB_STATUS",
            "/api/scrabs",
            "PUT",
            dto.getUserEmail(),
            request,
            HttpStatus.OK.value(),
            end - start
        );

        return ResponseEntity.ok().build();
    }
}
