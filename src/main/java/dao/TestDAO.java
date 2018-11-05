package dao;

import dto.TestTypes;
import entity.GroupEntity;
import entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TestDAO extends JpaRepository<Test, Long> {

    List<Test> findAllByType(TestTypes type);

    Set<Test> findAllByGroupsIn(List<GroupEntity> groups);
}
