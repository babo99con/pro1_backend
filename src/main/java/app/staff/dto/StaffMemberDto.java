package app.staff.dto;

import lombok.Data;

@Data
public class StaffMemberDto {
    private Long userId;
    private String staffNo;
    private String staffType;
    private Long deptId;
}