package dao;

import dto.TestTypes;
import entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestDAO extends JpaRepository<Test, Long> {

    List<Test> findAllByType(TestTypes type);
}
