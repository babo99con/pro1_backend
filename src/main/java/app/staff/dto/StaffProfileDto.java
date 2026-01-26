package app.staff.dto;

import lombok.Data;

@Data
public class StaffProfileDto {
    private Long userId;
    private String licenseNo;
    private Long profileImageObjectId;
}