package dao;

import config.ConnectionPool;

import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class AbstractDAO<E, K> {
    protected ResourceBundle sqlQueries = ResourceBundle.getBundle("sql_queries_dao");
    protected ConnectionPool pool;

    {
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract E add(E entity);

    public abstract E get(K id);

    public abstract void remove(K id);

}
