package app.common.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalExceptionHandler<T> {

    private boolean success;
    private String message;
    private T result;


}
