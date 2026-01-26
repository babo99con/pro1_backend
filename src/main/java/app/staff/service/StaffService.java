package app.staff.service;

import app.staff.dto.DepartmentDto;
import app.staff.dto.StaffMemberDto;
import app.staff.dto.StaffProfileDto;

public interface StaffService {
    DepartmentDto createDepartmentJpa(DepartmentDto dto);
    DepartmentDto createDepartmentMybatis(DepartmentDto dto);
    StaffMemberDto createStaffMemberJpa(StaffMemberDto dto);
    StaffMemberDto createStaffMemberMybatis(StaffMemberDto dto);
    StaffProfileDto createStaffProfileJpa(StaffProfileDto dto);
    StaffProfileDto createStaffProfileMybatis(StaffProfileDto dto);
}