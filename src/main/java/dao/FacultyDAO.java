package dao;

import entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyDAO extends JpaRepository<Faculty, Long> {
    Faculty findByName(String name);
}
