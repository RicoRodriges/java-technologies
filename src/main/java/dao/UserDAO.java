package dao;

import entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAO extends AbstractDAO<User, Long> {

    private final static String NAME = "name";

    public UserDAO(EntityManager entityManager) {
        super(entityManager);
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
