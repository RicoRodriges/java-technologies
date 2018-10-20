package dao;

import entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class QuestionDAO extends AbstractDAO<Question, Long> {

    public QuestionDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Question> getEntityClass() {
        return Question.class;
    }
}
