package board.employee.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import board.employee.jpa.EmployeeEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeDto {

    private Long id; // PK
    private String employeeId; // Employee identifier
    private String name; // Name
    private String emailLocal; // Email local-part
    private String emailDomain; // Email domain
    private String department; // Department
    private String gender; // "M" or "F"
    private LocalDate birthDate; // Birth date
    private String phonePrefix; // e.g. 010
    private String phoneMiddle;
    private String phoneLast;
    private String zipCode; // Postal code
    private String address1; // Primary address
    private String address2; // Address detail
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getEmail() {
        if (emailLocal == null || emailDomain == null) {
            return null;
        }
        return emailLocal + "@" + emailDomain;
    }

    public String getPhone() {
        if (phonePrefix == null || phoneMiddle == null || phoneLast == null) {
            return null;
        }
        return phonePrefix + "-" + phoneMiddle + "-" + phoneLast;
    }

}
