package board.employee.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.employee.service.EmployeeJpaService;
import board.employee.jpa.EmployeeEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/jpa/employees")
@AllArgsConstructor
@Tag(name = "Employees (JPA)", description = "Employee CRUD API powered by Spring Data JPA")
public class EmployeeJpaController {

    private final EmployeeJpaService employeeJpaService;

    @Operation(summary = "List all employees (JPA)")
    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> list() {
        List<EmployeeEntity> list = employeeJpaService.getEmployees();
        
        if (list == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get a single employee by id (JPA)")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> get(@PathVariable Long id) {
        EmployeeEntity employee = employeeJpaService.getEmployee(id);
        
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(employee);
    }

    @Operation(summary = "Search employees by condition and value (JPA)")
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeEntity>> searchByCondition(@RequestParam("condition") String condition,
                                                                  @RequestParam(value = "value") String value) {
        List<EmployeeEntity> list = employeeJpaService.getEmployeesByCondition(condition, value);
        
        if (list == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Create a new employee (JPA)")
    @PostMapping
    public ResponseEntity<EmployeeEntity> create(@RequestBody EmployeeEntity newEmployee) {
        EmployeeEntity created = employeeJpaService.createEmployee(newEmployee);
//        URI location = URI.create("/api/jpa/employees/" + created.getId());
//        return ResponseEntity.created(location).body(created);
        
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update an existing employee (JPA)")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEntity> update(@PathVariable Long id, @RequestBody EmployeeEntity newEmployee) {
        EmployeeEntity updated = employeeJpaService.updateEmployee(id, newEmployee);
        
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an employee (JPA)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeJpaService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
