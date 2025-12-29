package board.employee.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "employee_id", nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private String name;

    private String emailLocal;
    private String emailDomain;
    private String department;
    private String gender; // "M" or "F"
    private LocalDate birthDate;
    private String phonePrefix;
    private String phoneMiddle;
    private String phoneLast;
    private String zipCode;
    private String address1;
    private String address2;

    private char deleted_yn = 'Y';

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    // insert 하기전에 
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    // update 하기전에 
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void update(EmployeeEntity newEmployee) {
        this.employeeId = newEmployee.getEmployeeId();
        this.name = newEmployee.getName();
        this.emailLocal = newEmployee.getEmailLocal();
        this.emailDomain = newEmployee.getEmailDomain();
        this.department = newEmployee.getDepartment();
        this.gender = newEmployee.getGender();
        this.birthDate = newEmployee.getBirthDate();
        this.phonePrefix = newEmployee.getPhonePrefix();
        this.phoneMiddle = newEmployee.getPhoneMiddle();
        this.phoneLast = newEmployee.getPhoneLast();
        this.zipCode = newEmployee.getZipCode();
        this.address1 = newEmployee.getAddress1();
        this.address2 = newEmployee.getAddress2();
        }

        // 현직인지 검사
        public boolean isActive()
        {
            if(this.getDeleted_yn() == 'Y')
                return true;
            return false;
        }

}
