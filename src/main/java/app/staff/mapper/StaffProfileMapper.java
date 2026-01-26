package app.staff.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import app.staff.domain.StaffProfile;

public interface StaffProfileMapper {
    @Insert("INSERT INTO staff_profiles (user_id, license_no, profile_image_object_id) VALUES (#{userId}, #{licenseNo}, #{profileImageObjectId})")
    void insertStaffProfile(StaffProfile profile);

    @Select("SELECT * FROM staff_profiles WHERE user_id = #{userId}")
    StaffProfile selectStaffProfileByUserId(Long userId);
}