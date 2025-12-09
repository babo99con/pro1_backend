package board.employee.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.employee.dto.EmployeeDto;
import board.employee.mapper.EmployeeMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getEmployees() {
        return employeeMapper.findAll();
    }

    @Override
    public List<EmployeeDto> getEmployeesByCondition(
            @Param("condition") String condition,
            @Param("value") String value) {

        if (condition == null || condition.trim().isEmpty()) {
            throw new IllegalArgumentException("condition is required.");
        }

        return employeeMapper.findAllByCondition(condition, value);
    }

    @Override
    public EmployeeDto getEmployee(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto newEmployee) {

        if (newEmployee.getEmployeeId() == null) {
            throw new IllegalArgumentException("employeeId is required.");
        }
        EmployeeDto existingEmployee  = employeeMapper.findByEmployeeId(newEmployee.getEmployeeId());

        if (existingEmployee  != null) {
            throw new IllegalArgumentException("employeeId already exists.");
        }

        employeeMapper.insert(newEmployee);

        return employeeMapper.findById(newEmployee.getId());
    }

    @Override
    public EmployeeDto updateEmployee(Long newId, EmployeeDto newEmployee) {

        newEmployee.setId(newId);

        // employeeId 변경 시 중복 검사
        if (newEmployee.getEmployeeId() != null) {

            EmployeeDto existingEmployee = employeeMapper.findByEmployeeId(newEmployee.getEmployeeId());

            // 업데이트할 자리에 데이터가 있고,
            // existing 10번이 이미 존재한 경우
            // new 5번이 10번 바꾸려고할 때 오류
            if (existingEmployee != null && !existingEmployee.getId().equals(newId)) {
                throw new IllegalArgumentException("employeeId already exists.");
            }
        }

        int updated = employeeMapper.update(newEmployee);

        if (updated == 0) {
            return null;
        }

        return employeeMapper.findById(newId);
    }


    @Override
    public void deleteEmployee(Long id) {
        employeeMapper.delete(id);
    }

}
