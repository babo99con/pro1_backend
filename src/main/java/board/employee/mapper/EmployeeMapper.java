package board.employee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.employee.dto.EmployeeDto;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDto> findAll();

    EmployeeDto findById(@Param("id") Long id);

    EmployeeDto findByEmployeeId(@Param("employeeId") String employeeId);

    void insert(EmployeeDto employee);

    int update(EmployeeDto employee);

    int delete(@Param("id") Long id);
}
