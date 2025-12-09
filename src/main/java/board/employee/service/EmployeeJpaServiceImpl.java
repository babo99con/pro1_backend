package board.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import board.employee.jpa.EmployeeEntity;
import board.employee.jpa.EmployeeRepository;

@Service
@Transactional
public class EmployeeJpaServiceImpl implements EmployeeJpaService {

    private final EmployeeRepository employeeRepository;

    public EmployeeJpaServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeEntity> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeEntity> getEmployeesByCondition(String condition, String value) {
        if (!StringUtils.hasText(condition)) {
            throw new IllegalArgumentException("condition is required.");
        }

        if (!StringUtils.hasText(value)) {
            return getEmployees();
        }

        List<EmployeeEntity> results;
        switch (condition.toLowerCase()) {
            case "name":
                results = employeeRepository.findByNameContainingIgnoreCase(value);
                break;
            case "department":
                results = employeeRepository.findByDepartment(value);
                break;
            case "employeeid":
                Optional<EmployeeEntity> byEmployeeId = employeeRepository.findByEmployeeId(value);
                results = byEmployeeId.map(List::of).orElseGet(List::of);
                break;
            default:
                throw new IllegalArgumentException("Unsupported condition: " + condition);
        }

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeEntity getEmployee(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public EmployeeEntity createEmployee(EmployeeEntity newEmployee) {
        if (!StringUtils.hasText(newEmployee.getEmployeeId())) {
            throw new IllegalArgumentException("employeeId is required.");
        }

        employeeRepository.findByEmployeeId(newEmployee.getEmployeeId()).ifPresent(found -> {
            throw new IllegalArgumentException("employeeId already exists.");
        });

        return employeeRepository.save(newEmployee);
    }

    @Override
    public EmployeeEntity updateEmployee(Long id, EmployeeEntity newEmployee) {
        EmployeeEntity existing = employeeRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        if (StringUtils.hasText(newEmployee.getEmployeeId())) {
            Optional<EmployeeEntity> byEmployeeId = employeeRepository.findByEmployeeId(newEmployee.getEmployeeId());
            if (byEmployeeId.isPresent() && !byEmployeeId.get().getId().equals(id)) {
                throw new IllegalArgumentException("employeeId already exists.");
            }
        }

        existing.setEmployeeId(newEmployee.getEmployeeId());
        existing.setName(newEmployee.getName());
        existing.setEmailLocal(newEmployee.getEmailLocal());
        existing.setEmailDomain(newEmployee.getEmailDomain());
        existing.setDepartment(newEmployee.getDepartment());
        existing.setGender(newEmployee.getGender());
        existing.setBirthDate(newEmployee.getBirthDate());
        existing.setPhonePrefix(newEmployee.getPhonePrefix());
        existing.setPhoneMiddle(newEmployee.getPhoneMiddle());
        existing.setPhoneLast(newEmployee.getPhoneLast());
        existing.setZipCode(newEmployee.getZipCode());
        existing.setAddress1(newEmployee.getAddress1());
        existing.setAddress2(newEmployee.getAddress2());

        return employeeRepository.save(existing);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
