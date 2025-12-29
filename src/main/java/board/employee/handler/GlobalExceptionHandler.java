package board.employee.handler;

import board.employee.common.ApiResponse;
import board.employee.exception.DuplicateResourceException;
import board.employee.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전체 예외 처리기
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    /**
     * 404 - 리소스 없음
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(
                        ApiResponse.fail("RESOURCE_NOT_FOUND")
                );
    }

    /**
     * 409 - 중복 / 충돌
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(DuplicateResourceException ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.fail("DUPLICATE_RESOURCE")
                );
    }

    /**
     * 400 - 부적절한 인자
     */    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(400)
                .body(
                        ApiResponse.fail(ex.getMessage())
                );
    }

    /**
     * 나머지 예외 (fallback)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body(
                        ApiResponse.fail(ex.getMessage())
                );
    }
}
