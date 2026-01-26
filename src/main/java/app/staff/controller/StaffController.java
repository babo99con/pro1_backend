package app.staff.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import app.staff.dto.StaffMemberDto;
import app.staff.dto.StaffProfileDto;
import app.staff.service.StaffService;
import app.common.ApiResponse;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // JPA 경로
    @PostMapping("/members")
    public ResponseEntity<ApiResponse<StaffMemberDto>> createStaffMemberJpa(@RequestBody StaffMemberDto dto) {
        StaffMemberDto res = staffService.createStaffMemberJpa(dto);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    @PostMapping("/profiles")
    public ResponseEntity<ApiResponse<StaffProfileDto>> createStaffProfileJpa(@RequestBody StaffProfileDto dto) {
        StaffProfileDto res = staffService.createStaffProfileJpa(dto);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    // MyBatis 경로
    @PostMapping("/departments-mybatis")
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartmentMybatis(@RequestBody DepartmentDto dto) {
        DepartmentDto res = staffService.createDepartmentMybatis(dto);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    @PostMapping("/members-mybatis")
    public ResponseEntity<ApiResponse<StaffMemberDto>> createStaffMemberMybatis(@RequestBody StaffMemberDto dto) {
        StaffMemberDto res = staffService.createStaffMemberMybatis(dto);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }

    @PostMapping("/profiles-mybatis")
    public ResponseEntity<ApiResponse<StaffProfileDto>> createStaffProfileMybatis(@RequestBody StaffProfileDto dto) {
        StaffProfileDto res = staffService.createStaffProfileMybatis(dto);
        return ResponseEntity.ok(ApiResponse.ok(res));
    }
}