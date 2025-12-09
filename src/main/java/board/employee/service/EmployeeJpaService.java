package board.employee.service;

import java.util.List;

import board.employee.jpa.EmployeeEntity;

public interface EmployeeJpaService {

    List<EmployeeEntity> getEmployees();

    List<EmployeeEntity> getEmployeesByCondition(String condition, String value);

    EmployeeEntity getEmployee(Long id);

    EmployeeEntity createEmployee(EmployeeEntity employee);

    EmployeeEntity updateEmployee(Long id, EmployeeEntity employee);

    void deleteEmployee(Long id);
}
