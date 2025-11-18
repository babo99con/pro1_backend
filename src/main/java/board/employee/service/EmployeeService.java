package board.employee.service;

import java.util.List;

import board.employee.dto.EmployeeDto;

public interface EmployeeService {

    List<EmployeeDto> getEmployees();

    EmployeeDto getEmployee(Long id);

    EmployeeDto createEmployee(EmployeeDto employee);

    EmployeeDto updateEmployee(Long id, EmployeeDto employee);

    void deleteEmployee(Long id);
}
