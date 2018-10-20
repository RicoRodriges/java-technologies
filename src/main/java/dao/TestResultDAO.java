package dao;

import entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultDAO extends JpaRepository<TestResult, Long> {

    List<TestResult> findAllByUserId(Long userId);
}