package dao;

import entity.Test;
import entity.TestTypes;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TestDAO extends AbstractDAO<Test, Long> {

    private final static String TYPE = "type";

    public TestDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Test> findAllByType(TestTypes type) {
        return find(Test.class, ((builder, root) -> builder.equal(root.get(TYPE), type)));
    }

    @Override
    protected Class<Test> getEntityClass() {
        return Test.class;
    }
}
