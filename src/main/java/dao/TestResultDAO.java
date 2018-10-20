package dao;

import entity.TestResult;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TestResultDAO extends AbstractDAO<TestResult, Long> {

    private final static String USERID = "userId";

    public TestResultDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<TestResult> findAllByUserId(Long userId) {
        return find(TestResult.class, ((builder, root) -> builder.equal(root.get(USERID), userId)));
    }

    @Override
    protected Class<TestResult> getEntityClass() {
        return TestResult.class;
    }
}