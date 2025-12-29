package board.employee.exception;

/**
 * 요청한 리소스를 찾을 수 없을 때 발생하는 예외
 *
 * 예)
 * - 존재하지 않는 employee id 조회
 * - 삭제 / 수정 대상이 이미 없는 경우
 *
 * HTTP 404 (Not Found) 로 매핑하는게 정상
 * 
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
