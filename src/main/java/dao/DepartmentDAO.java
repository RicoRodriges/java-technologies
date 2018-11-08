package dao;

import entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentDAO extends JpaRepository<Department, Long> {
    List<Department> findAllByFacultyId(Long faculty_id);
}
