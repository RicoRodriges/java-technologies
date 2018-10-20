package dao;

import entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDAO extends JpaRepository<Question, Long> {
}
