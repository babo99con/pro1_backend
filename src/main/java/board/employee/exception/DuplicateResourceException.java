package board.employee.exception;

/**
 * 이미 존재하는 리소스를 다시 생성하거나
 * 유니크해야 하는 값을 중복으로 사용하려 할 때 발생하는 예외
 *
 * 예)
 * - employeeId 중복 생성
 * - 수정 시 다른 엔티티가 이미 사용 중인 employeeId로 변경 시도
 *
 * HTTP 409 (Conflict) 로 매핑됨
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
