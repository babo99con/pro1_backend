package board.employee.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import board.employee.common.ApiResponse;
import board.employee.dto.EmployeeDto;
import board.employee.jpa.EmployeeEntity;
import board.employee.mapper.DTOMapper;
import board.employee.mapper.EmployeeMapper;
import board.employee.service.EmployeeJpaService;
import board.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jpa/employees")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
@Tag(name = "Employees (JPA)", description = "Employee CRUD API powered by Spring Data JPA")
public class EmployeeJpaController {

    private final EmployeeJpaService employeeJpaService;
    private final EmployeeService employeeService;

    @Operation(summary = "List all employees")
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>>openEmployeesList() {

        log.info("직원 전체 목록 조회 요청(GET /api/jpa/employees)");

        List<EmployeeEntity> list = employeeJpaService.getEmployees();

        // 한번 예제로 ApiResponse에 fail로 처리.
        return ResponseEntity.ok(
                ApiResponse.success("전체 직원 조회 성공", DTOMapper.toDtoList(list))
        );
    }


    @Operation(summary = "Search employees by condition and value")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> searchByCondition(@RequestParam("condition") String condition,
                                                                            @RequestParam("value") String value) {

        log.info("직원 조건 검색 요청 - condition={}, value={}", condition, value);

        // mybatis가 자동으로 map객체로 집어넣는다.
        List<EmployeeDto> list = employeeService.getEmployeesByCondition(condition, value);

        if(list ==null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse<>(false, "조건 조회 실패", list)
                    );

        return ResponseEntity.ok(
                new ApiResponse<>(true, "검색 조회 성공", list)
        );
    }

    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> openEmployeeDetail(@PathVariable Long id) {

        log.info("[GET] /api/jpa/employees/{}", id);

        EmployeeEntity employee = employeeJpaService.getEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.success("단일 직원 조회 성공", DTOMapper.toDto(employee))
        );
    }

    @Operation(summary = "Create employee")
    @PostMapping
    public ResponseEntity<ApiResponse<URI>> createEmployee(@RequestBody EmployeeEntity newEmployee) {

        log.info("[POST] /api/jpa/employees");

        EmployeeEntity created = employeeJpaService.createEmployee(newEmployee);
        URI location = URI.create("/api/jpa/employees/" + created.getId());

        return ResponseEntity
                .created(location)
                .body(ApiResponse.success("신규 직원 생성 성공", location));
    }

    @Operation(summary = "Update employee")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeEntity newEmployee) {

        log.info("[PUT] /api/jpa/employees/{}", id);

        EmployeeEntity updated =
                employeeJpaService.updateEmployee(id, newEmployee);

        return ResponseEntity.ok(
                ApiResponse.success("직원 수정 성공", DTOMapper.toDto(updated))
        );
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {

        log.info("[DELETE] /api/jpa/employees/{}", id);

        employeeJpaService.deleteEmployee(id);

        return ResponseEntity.ok(
                ApiResponse.success("직원 삭제 성공", null)
        );
    }
}
