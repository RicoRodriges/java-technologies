package dao;

import entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityDAO extends JpaRepository<University, Long> {
}
