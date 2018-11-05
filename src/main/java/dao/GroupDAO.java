package dao;

import entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupDAO extends JpaRepository<GroupEntity, Long> {
}
