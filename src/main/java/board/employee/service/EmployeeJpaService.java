package board.employee.service;

import java.util.List;
import java.util.Optional;

import board.employee.jpa.EmployeeEntity;

public interface EmployeeJpaService {

    List<EmployeeEntity> getEmployees();

    List<EmployeeEntity> getEmployeesByCondition(String condition, String value);

    Optional<EmployeeEntity> getEmployeeByEmployeeId(String employeeId) ;

    EmployeeEntity getEmployee(Long id);

    EmployeeEntity createEmployee(EmployeeEntity employee);

    EmployeeEntity updateEmployee(Long id, EmployeeEntity employee);

    void deleteEmployee(Long id);
}
