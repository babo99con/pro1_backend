package app.staff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.staff.domain.StaffProfile;

@Repository
public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long> {
}