package dao;

import entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsDAO extends JpaRepository<GroupEntity, Long> {
}
