package board.employee.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    boolean success;
    String message;
    T result;

    /** 성공 응답 생성용 static factory method */
    public static <T> ApiResponse<T> success(String message, T result) {
        return new ApiResponse<>(true, message, result);
    }

    /** 실패 응답 생성용 static factory method */
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }

    /** 실패 응답 + 객체 생성용 static factory method */
    public static <T> ApiResponse<T> fail(String message, T result) {
        return new ApiResponse<>(false, message, result);
    }
}
