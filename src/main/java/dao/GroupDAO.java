package dao;

import entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDAO extends JpaRepository<GroupEntity, Long> {
    List<GroupEntity> findAllByDepartmentId(Long department_id);
}
