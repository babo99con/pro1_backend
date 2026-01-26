package app.staff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.staff.domain.StaffMember;

@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {
}