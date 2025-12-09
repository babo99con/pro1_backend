package board.employee.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmployeeId(String employeeId);

    List<EmployeeEntity> findByNameContainingIgnoreCase(String name);

    List<EmployeeEntity> findByDepartment(String department);
}
