package dao;

import entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserDAO extends AbstractDAO<User, Long> {

    private final static String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public User findByName(String name) {
        List<User> users = find(User.class, ((builder, root) -> builder.equal(root.get(NAME), name)));
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
