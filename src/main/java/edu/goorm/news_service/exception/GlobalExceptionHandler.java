package edu.goorm.news_service.exception;

import edu.goorm.news_service.logger.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, HttpServletRequest request) {
        // ✅ 로그 기록
        CustomLogger.logRequest(
                "ERROR",
                request.getRequestURI(),
                request.getMethod(),
                request.getHeader("X-User-Email"),
                "Exception: " + ex.getMessage(),
                request
        );

        // 로그 외에도 콘솔에 출력하고 싶다면 (선택)
        ex.printStackTrace();

        // 사용자에게는 간단한 메시지 반환
        return ResponseEntity
                .status(500)
                .body("서버에서 오류가 발생했습니다.");
    }
}
