package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ResourceBundle;

@RequiredArgsConstructor
public abstract class AbstractDAO<E, K> {
    protected final ResourceBundle sqlQueries = ResourceBundle.getBundle("sql_queries_dao");
    protected final JdbcTemplate jdbcTemplate;

    public abstract E add(E entity);

    public abstract E get(K id);

    public abstract void remove(K id);

}
