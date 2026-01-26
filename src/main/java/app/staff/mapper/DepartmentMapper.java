package app.staff.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import app.staff.domain.Department;
import java.util.List;

public interface DepartmentMapper {
    @Insert("INSERT INTO departments (dept_code, dept_name, is_active, created_at) VALUES (#{deptCode}, #{deptName}, #{isActive}, NOW())")
    void insertDepartment(Department dept);

    @Select("SELECT id, dept_code AS deptCode, dept_name AS deptName, is_active AS isActive FROM departments")
    List<Department> selectAllDepartments();
}