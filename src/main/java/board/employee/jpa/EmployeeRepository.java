package board.employee.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // employeeId 에 정확히 매칭되는 1개의 employeeEntity를 담는 리스트를 반환.
    // @Query("select e from EmployeeEntity e where e.employeeId = :employeeId")
    Optional<EmployeeEntity> searchByEmployeeId(@Param("employeeId") String employeeId);

    // 이름 부분 검색 (대소문자 무시), 여러개의 employeeEntity를 담을 수 있음. 물론 없을 수 있음.
    // @Query("select e from EmployeeEntity e where lower(e.name) like lower(concat('%', :name, '%'))")
    List<EmployeeEntity> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // 부서 정확 검색
    // @Query("select e from EmployeeEntity e where e.department = :department")
    List<EmployeeEntity> searchByDepartment(@Param("department") String department);

}
