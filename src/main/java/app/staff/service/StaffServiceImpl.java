package app.staff.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import app.staff.dto.DepartmentDto;
import app.staff.dto.StaffMemberDto;
import app.staff.dto.StaffProfileDto;
import app.staff.domain.Department;
import app.staff.domain.StaffMember;
import app.staff.domain.StaffProfile;
import app.staff.repository.DepartmentRepository;
import app.staff.repository.StaffMemberRepository;
import app.staff.repository.StaffProfileRepository;
import app.staff.mapper.DepartmentMapper;
import app.staff.mapper.StaffMemberMapper;
import app.staff.mapper.StaffProfileMapper;

@Service
public class StaffServiceImpl implements StaffService {

    private final DepartmentRepository deptRepo;
    private final StaffMemberRepository memberRepo;
    private final StaffProfileRepository profileRepo;

    // MyBatis mappers (optional, 패턴 예시)
    private final DepartmentMapper departmentMapper;
    private final StaffMemberMapper staffMemberMapper;
    private final StaffProfileMapper staffProfileMapper;

    @Autowired
    public StaffServiceImpl(DepartmentRepository deptRepo,
                          StaffMemberRepository memberRepo,
                          StaffProfileRepository profileRepo,
                          DepartmentMapper departmentMapper,
                          StaffMemberMapper staffMemberMapper,
                          StaffProfileMapper staffProfileMapper) {
        this.deptRepo = deptRepo;
        this.memberRepo = memberRepo;
        this.profileRepo = profileRepo;
        this.departmentMapper = departmentMapper;
        this.staffMemberMapper = staffMemberMapper;
        this.staffProfileMapper = staffProfileMapper;
    }

    // JPA 경로
    @Override
    @Transactional
    public DepartmentDto createDepartmentJpa(DepartmentDto dto) {
        Department d = new Department();
        d.setDeptCode(dto.getDeptCode());
        d.setDeptName(dto.getDeptName());
        d.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        deptRepo.save(d);
        DepartmentDto res = new DepartmentDto();
        res.setDeptCode(d.getDeptCode());
        res.setDeptName(d.getDeptName());
        res.setIsActive(d.getIsActive());
        return res;
    }

    // MyBatis 경로
    @Override
    @Transactional // 필요 시 별도 트랜잭션 경계
    public DepartmentDto createDepartmentMybatis(DepartmentDto dto) {
        Department d = new Department();
        d.setDeptCode(dto.getDeptCode());
        d.setDeptName(dto.getDeptName());
        d.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        departmentMapper.insertDepartment(d);
        DepartmentDto res = new DepartmentDto();
        res.setDeptCode(d.getDeptCode());
        res.setDeptName(d.getDeptName());
        res.setIsActive(d.getIsActive());
        return res;
    }

    // JPA 경로
    @Override
    @Transactional
    public StaffMemberDto createStaffMemberJpa(StaffMemberDto dto) {
        StaffMember s = new StaffMember();
        s.setUserId(dto.getUserId());
        s.setStaffNo(dto.getStaffNo());
        s.setStaffType(dto.getStaffType());
        s.setDeptId(dto.getDeptId());
        s.setIsActive(true);
        memberRepo.save(s);
        StaffMemberDto out = new StaffMemberDto();
        out.setUserId(s.getUserId());
        out.setStaffNo(s.getStaffNo());
        out.setStaffType(s.getStaffType());
        out.setDeptId(s.getDeptId());
        return out;
    }

    // MyBatis 경로
    @Override
    public StaffMemberDto createStaffMemberMybatis(StaffMemberDto dto) {
        StaffMember s = new StaffMember();
        
		s.setUserId(dto.getUserId());
        s.setStaffNo(dto.getStaffNo());
        s.setStaffType(dto.getStaffType());
        s.setDeptId(dto.getDeptId());
        s.setIsActive(true);
        staffMemberMapper.insertStaffMember(s);
        
		StaffMemberDto out = new StaffMemberDto();
        out.setUserId(s.getUserId());
        out.setStaffNo(s.getStaffNo());
        out.setStaffType(s.getStaffType());
        out.setDeptId(s.getDeptId());
        
		return out;
    }

    // JPA 경로
    @Override
    @Transactional
    public StaffProfileDto createStaffProfileJpa(StaffProfileDto dto) {
        StaffProfile p = new StaffProfile();
        p.setUserId(dto.getUserId());
        p.setLicenseNo(dto.getLicenseNo());
        p.setProfileImageObjectId(dto.getProfileImageObjectId());
        profileRepo.save(p);
        StaffProfileDto out = new StaffProfileDto();
        out.setUserId(p.getUserId());
        out.setLicenseNo(p.getLicenseNo());
        out.setProfileImageObjectId(p.getProfileImageObjectId());
        return out;
    }

    // MyBatis 경로
    @Override
    public StaffProfileDto createStaffProfileMybatis(StaffProfileDto dto) {
        StaffProfile p = new StaffProfile();
        p.setUserId(dto.getUserId());
        p.setLicenseNo(dto.getLicenseNo());
        p.setProfileImageObjectId(dto.getProfileImageObjectId());
        staffProfileMapper.insertStaffProfile(p);
        StaffProfileDto out = new StaffProfileDto();
        out.setUserId(p.getUserId());
        out.setLicenseNo(p.getLicenseNo());
        out.setProfileImageObjectId(p.getProfileImageObjectId());
        return out;
    }
}