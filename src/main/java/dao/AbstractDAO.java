package dao;

import config.ConnectionPool;
import lombok.RequiredArgsConstructor;

import java.util.ResourceBundle;

@RequiredArgsConstructor
public abstract class AbstractDAO<E, K> {
    protected final ResourceBundle sqlQueries = ResourceBundle.getBundle("sql_queries_dao");
    protected final ConnectionPool pool;

    public abstract E add(E entity);

    public abstract E get(K id);

    public abstract void remove(K id);

}
