package board.employee.handler;

import board.employee.common.ApiResponse;
import board.employee.exception.employee.EmployeeHrProcessingException;
import board.employee.exception.employee.EmployeeInactiveException;
import board.employee.exception.employee.EmployeeOnLeaveException;
import board.employee.exception.employee.EmployeePayrollClosedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 직원 도메인 전용 예외 처리기
 * - "직원은 존재하지만, 현재 상태에서는 처리 불가"한 경우만 담당
 */
@RestControllerAdvice
public class EmployeeExceptionHandler {

    /**
     * 퇴사 / 비활성 직원
     */
    @ExceptionHandler(EmployeeInactiveException.class)
    public ResponseEntity<ApiResponse<Void>> handleInactive(EmployeeInactiveException ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.fail("EMPLOYEE_INACTIVE")
                );
    }

    /**
     * 인사 처리 중
     */
    @ExceptionHandler(EmployeeHrProcessingException.class)
    public ResponseEntity<ApiResponse<Void>> handleHrProcessing(EmployeeHrProcessingException ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.fail("EMPLOYEE_UNDER_HR_PROCESS")
                );
    }

    /**
     * 급여 마감 이후 수정 불가
     */
    @ExceptionHandler(EmployeePayrollClosedException.class)
    public ResponseEntity<ApiResponse<Void>> handlePayrollClosed(EmployeePayrollClosedException ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.fail("EMPLOYEE_PAYROLL_CLOSED")
                );
    }

    /**
     * 휴직 중 기능 제한
     */
    @ExceptionHandler(EmployeeOnLeaveException.class)
    public ResponseEntity<ApiResponse<Void>> handleOnLeave(EmployeeOnLeaveException ex) {
        return ResponseEntity
                .status(409)
                .body(
                        ApiResponse.fail("EMPLOYEE_ON_LEAVE")
                );
    }
}
