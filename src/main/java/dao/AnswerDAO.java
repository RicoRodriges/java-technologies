package dao;

import entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerDAO extends JpaRepository<Answer, Long> {
}
