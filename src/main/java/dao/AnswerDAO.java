package dao;

import entity.Answer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AnswerDAO extends AbstractDAO<Answer, Long> {

    public AnswerDAO(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Answer> getEntityClass() {
        return Answer.class;
    }
}
