package board.employee.exception.employee;

public class EmployeeInactiveException extends RuntimeException {
    public EmployeeInactiveException(String message) {
        super(message);
    }
}
