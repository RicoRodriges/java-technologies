package dao;

import entity.TestResult;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TestResultDAO extends AbstractDAO<TestResult, Long> {

    private final static String USERID = "user";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<TestResult> findAllByUserId(Long userId) {
        return find(TestResult.class, ((builder, root) -> builder.equal(root.get(USERID), userId)));
    }

    @Override
    protected Class<TestResult> getEntityClass() {
        return TestResult.class;
    }
}