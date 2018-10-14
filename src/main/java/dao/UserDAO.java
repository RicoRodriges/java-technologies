package dao;

import entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO extends AbstractDAO<User, Long> {

    private final static Logger log = LogManager.getLogger(UserDAO.class);

    public UserDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public User add(User entity) {
        jdbcTemplate.update(sqlQueries.getString("ADD_USER"),
                entity.getName(), entity.getPassword(), entity.getIsTutor());
        log.info("User " + entity + " was added");
        return get(entity.getName());
    }

    @Override
    public User get(Long id) {
        RowMapper<User> map = (rs, rowNum) -> getUserById(id, rs);
        List<User> users = jdbcTemplate.query(sqlQueries.getString("GET_USER"), map, id);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public User get(String name) {
        RowMapper<User> map = (rs, rowNum) -> getUserByName(name, rs);
        List<User> users = jdbcTemplate.query(sqlQueries.getString("GET_USER_BY_NAME"), map, name);
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public List<User> getAll() {
        RowMapper<User> map = (rs, rowNum) -> getUserById(rs.getLong("id"), rs);
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_USERS"), map);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_USER"), id);
        log.info("User(id:" + id + ") was added");
    }

    private User getUserById(Long id, ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String pass = rs.getString("pass");
        boolean tutor = rs.getBoolean("isTutor");
        User user = new User(name, pass, tutor);
        user.setId(id);
        return user;
    }

    private User getUserByName(String name, ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String pass = rs.getString("pass");
        boolean tutor = rs.getBoolean("isTutor");
        User user = new User(name, pass, tutor);
        user.setId(id);
        return user;
    }
}
