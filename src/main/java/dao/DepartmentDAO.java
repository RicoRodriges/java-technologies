package dao;

import entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentDAO extends JpaRepository<Department, Long> {
    Department findByName(String name);
}
