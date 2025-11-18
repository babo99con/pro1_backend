package board.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public EmployeeDto getEmployee(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employee) {
        Assert.notNull(employee.getEmployeeId(), "employeeId is required.");
        EmployeeDto duplicated = employeeMapper.findByEmployeeId(employee.getEmployeeId());
        if (duplicated != null) {
            throw new IllegalArgumentException("employeeId already exists.");
        }
        employeeMapper.insert(employee);
        return employeeMapper.findById(employee.getId());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {
        employee.setId(id);
        if (employee.getEmployeeId() != null) {
            EmployeeDto duplicated = employeeMapper.findByEmployeeId(employee.getEmployeeId());
            if (duplicated != null && !duplicated.getId().equals(id)) {
                throw new IllegalArgumentException("employeeId already exists.");
            }
        }
        int updated = employeeMapper.update(employee);
        if (updated == 0) {
            return null;
        }
        return employeeMapper.findById(id);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeMapper.delete(id);
    }
}
