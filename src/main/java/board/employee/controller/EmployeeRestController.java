package board.employee.controller;

import java.net.URI;
import java.util.List;

import board.employee.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import board.employee.dto.EmployeeDto;
import board.employee.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*") // Allow all origins (consider tightening in production)
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Employee CRUD API")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "List all employees")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> list() {

        List<EmployeeDto> list = employeeService.getEmployees();

        if (list == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse<>(false, "전체 조회 실패", list)
                    );

        return ResponseEntity.ok(
                new ApiResponse<>(true,"전체 조회 성공", list)
        );
    }

    @Operation(summary = "Get a single employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> get(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployee(id);
        if (employee == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse<>(false, "단일 조회 실패", employee)
                    );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, "단일 조회 성공", employee)
        );
    }

    // GET /search?condition={condition}&value={value}
    @Operation(summary = "Search employees by condition and value")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> searchByCondition(@RequestParam("condition") String condition,
                                                               @RequestParam("value") String value) {
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

    // POST /
    // {
    // ... employee
    // }
    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<ApiResponse<URI>> create(@RequestBody EmployeeDto employee) {
        EmployeeDto created = employeeService.createEmployee(employee);
        URI location = URI.create("/api/employees/" + created.getId());

         return ResponseEntity
                 .status(201)
                 .body(
                         new ApiResponse<>(true, "직원 생성 성공", location)
                 );

//        return ResponseEntity.ok(created);
    }

    // PUT /{id}
    // {
    // ... employee
    // }
    @Operation(summary = "Update an existing employee")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> update(@PathVariable Long id, @RequestBody EmployeeDto employee) {
        EmployeeDto updated = employeeService.updateEmployee(id, employee);

            if (updated == null) {
                return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            new ApiResponse<>(false, "직원 수정 실패",null)
                    );
        }
        return ResponseEntity.ok(
                new ApiResponse<>(true, "직원 수정 성공", updated)
        );
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        new ApiResponse<>(true, "직원 삭제 성공", null)
                );
    }


}
