package board.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import board.employee.exception.DuplicateResourceException;
import board.employee.exception.ResourceNotFoundException;
import board.employee.exception.employee.EmployeeInactiveException;
import board.employee.jpa.EmployeeEntity;
import board.employee.jpa.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class EmployeeJpaServiceImpl implements EmployeeJpaService {

    private final EmployeeRepository employeeRepository;

    /**
     * 전체 직원 조회
     */
    @Override
    public List<EmployeeEntity> getEmployees() {
        log.debug("전체 직원 조회 처리 시작");
        return employeeRepository.findAll();
    }

    /**
     * 조건 기반 직원 조회 (name, department)
     */
    @Override
    public List<EmployeeEntity> getEmployeesByCondition(String condition, String value) {

        log.debug("직원 조건 검색 처리 시작 - condition={}, value={}", condition, value);

        if (condition == null || condition.trim().isEmpty()) {
            log.warn("직원 조건 검색 실패 - condition이 비어 있음");
            throw new IllegalArgumentException("condition is required.");
        }

        if (value == null || value.trim().isEmpty()) {
            log.debug("직원 조건 검색 - value가 비어 있어 빈 목록 반환");
            return new ArrayList<>();
        }

        String key = condition.toLowerCase().trim();

        if (key.equals("name")) {
            log.info("직원 검색 분기 선택 - 이름 기준");
            return employeeRepository.findByNameContainingIgnoreCase(value);
        }

        if (key.equals("department")) {
            log.info("직원 검색 분기 선택 - 부서 기준");
            return employeeRepository.searchByDepartment(value);
        }

        log.warn("지원하지 않는 직원 검색 조건 - condition={}", condition);
        throw new IllegalArgumentException("Unsupported condition: " + condition);
    }

    /**
     * 사번(employeeId) 기반 단건 조회
     */
    @Override
    public Optional<EmployeeEntity> getEmployeeByEmployeeId(String employeeId) {

        log.debug("사번 기반 직원 조회 시도 - employeeId={}", employeeId);

        if (employeeId == null || employeeId.trim().isEmpty()) {
            log.warn("사번 기반 직원 조회 실패 - employeeId가 비어 있음");
            return Optional.empty(); // Optional이면서 null인거지, 완전한 null은 아님. 주의.
        }

        return employeeRepository.searchByEmployeeId(employeeId);
    }

    /**
     * ID 기반 단건 조회
     */
    @Override
    public EmployeeEntity getEmployee(Long id) {

        log.debug("직원 단건 조회 처리 - id={}", id);

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("직원 단건 조회 실패 - 존재하지 않는 id={}", id);
                    return new ResourceNotFoundException("employee not found. id=" + id);
                });

//        아래와 같이 Employee에만 발생할 수 있는 예외
        // ① 퇴사
        if (employee.isActive()) {
            // 이건 글로벌 아님.
            throw new EmployeeInactiveException("EMPLOYEE_INACTIVE");
        }

////        A. 인사 처리 중
//        if (employee.isHrProcessing()) {
//            throw new EmployeeBusinessException("EMPLOYEE_UNDER_HR_PROCESS", "...");
//        }
//
////        B. 급여 마감
//        if (employee.isPayrollClosed()) {
//            throw new EmployeeBusinessException("EMPLOYEE_PAYROLL_CLOSED", "...");
//        }
////        C. 휴가중 처리
//        if (employee.isLeave()) {
//            throw new EmployeeBusinessException("EMPLOYEE_ON_LEAVING", "...");
//        }


        return employee;
    }

    /**
     * 직원 생성
     */
    @Override
    public EmployeeEntity createEmployee(EmployeeEntity newEmployee) {

        String employeeId = newEmployee.getEmployeeId();
        log.info("신규 직원 생성 처리 시작 - employeeId={}", employeeId);

        if (employeeId == null || employeeId.trim().isEmpty()) {
            log.warn("신규 직원 생성 실패 - employeeId가 비어 있음");
            throw new IllegalArgumentException("employeeId is required.");
        }

        if (employeeRepository.searchByEmployeeId(employeeId).isPresent()) {
            log.warn("신규 직원 생성 실패 - employeeId 중복={}", employeeId);
            throw new DuplicateResourceException("employeeId already exists.");
        }

        return employeeRepository.save(newEmployee);
    }

    /**
     * 직원 수정
     */
    @Override
    public EmployeeEntity updateEmployee(Long id, EmployeeEntity newEmployee) {

        log.info("직원 정보 수정 처리 시작 - id={}", id);

        EmployeeEntity oldEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("직원 정보 수정 실패 - 존재하지 않는 id={}", id);
                    return new ResourceNotFoundException("employee not found. id=" + id);
                });

        String newEmployeeId = newEmployee.getEmployeeId();

        if (newEmployeeId != null && !newEmployeeId.trim().isEmpty()) {

            var found =
                    employeeRepository.searchByEmployeeId(newEmployeeId).orElse(null);

            if (found != null && !found.getId().equals(id)) {
                log.warn("직원 정보 수정 실패 - employeeId 중복={}", newEmployeeId);
                throw new DuplicateResourceException("employeeId already exists.");
            }
        }

        oldEmployee.update(newEmployee);
        return employeeRepository.save(oldEmployee);
    }

    /**
     * 직원 삭제
     */
    @Override
    public void deleteEmployee(Long id) {

        log.info("직원 삭제 처리 시작 - id={}", id);

        if (!employeeRepository.existsById(id)) {
            log.warn("직원 삭제 실패 - 존재하지 않는 id={}", id);
            throw new ResourceNotFoundException("employee not found. id=" + id);
        }

        employeeRepository.deleteById(id);
    }
}
