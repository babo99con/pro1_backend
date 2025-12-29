package board.employee.mapper;


import org.springframework.stereotype.Component;

import board.employee.dto.EmployeeDto;
import board.employee.jpa.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;
@Component
public class DTOMapper {

//        entity obj => dto obj
       public static EmployeeDto toDto(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        return EmployeeDto.builder()
                .id         (entity.getId())
                .employeeId (entity.getEmployeeId())
                .name       (entity.getName())
                .emailLocal (entity.getEmailLocal())
                .emailDomain(entity.getEmailDomain())
                .department (entity.getDepartment())
                .gender     (entity.getGender())
                .birthDate (entity.getBirthDate())
                .phonePrefix(entity.getPhonePrefix())
                .phoneMiddle(entity.getPhoneMiddle())
                .phoneLast(entity.getPhoneLast())
                .zipCode (entity.getZipCode())
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

//    entity list => dto list
    public static List<EmployeeDto> toDtoList(List<EmployeeEntity> EntityList) {
        if (EntityList == null) {
            return null;
        }

        List<EmployeeDto> result_list = new ArrayList<EmployeeDto>();

        for(int i=0; i<EntityList.size();i++)
        {
            result_list.add(toDto(EntityList.get(i)));
        }

        return result_list;
    }
}
