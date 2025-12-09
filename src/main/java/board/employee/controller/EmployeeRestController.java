package board.employee.controller;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<EmployeeDto>> list() {

        List<EmployeeDto> list = employeeService.getEmployees();

        if (list == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get a single employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployee(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Search employees by condition and value")
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchByCondition(@RequestParam("condition") String condition,
                                                               @RequestParam("value") String value) {
        // mybatis가 자동으로 map객체로 집어넣는다.
        List<EmployeeDto> list = employeeService.getEmployeesByCondition(condition, value);

        if(list ==null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Create a new employee")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employee) {
        EmployeeDto created = employeeService.createEmployee(employee);
//         URI location = URI.create("/api/employees/" + created.getId());
//         return ResponseEntity.created(location).body(created);

        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update an existing employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Long id, @RequestBody EmployeeDto employee) {
        EmployeeDto updated = employeeService.updateEmployee(id, employee);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
