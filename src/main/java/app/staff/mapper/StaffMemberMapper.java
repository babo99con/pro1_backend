package app.staff.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import app.staff.domain.StaffMember;

public interface StaffMemberMapper {
    @Insert("INSERT INTO staff_members (user_id, staff_no, staff_type, dept_id, is_active, hired_at) VALUES (#{userId}, #{staffNo}, #{staffType}, #{deptId}, #{isActive}, #{hiredAt})")
    void insertStaffMember(StaffMember staffMember);

    @Select("SELECT * FROM staff_members WHERE id = #{id}")
    StaffMember selectStaffMemberById(Long id);
}