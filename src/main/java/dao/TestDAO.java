package dao;

import dto.TestTypes;
import entity.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TestDAO extends AbstractDAO<Test, Long> {

    private final static String TYPE = "type";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Test> findAllByType(TestTypes type) {
        return find(Test.class, ((builder, root) -> builder.equal(root.get(TYPE), type)));
    }

    @Override
    protected Class<Test> getEntityClass() {
        return Test.class;
    }
}
