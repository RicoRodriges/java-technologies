package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;

public class ConnectionPool {
    private final static Logger log = LogManager.getLogger(ConnectionPool.class);

    private final String H2_URL;
    private final String H2_USER;
    private final String H2_PASS;
    private final int MAX_CONNECTION;
    private final Deque<Connection> freeConnections;

    ConnectionPool(String url, String user, String pass, int max) {
        H2_URL = url;
        H2_USER = user;
        H2_PASS = pass;
        MAX_CONNECTION = max;
        freeConnections = new LinkedList<>();
    }

    @PostConstruct
    public void init() throws SQLException {
        for (int i = 0; i < MAX_CONNECTION; i++) {
            freeConnections.add(DriverManager.getConnection(H2_URL, H2_USER, H2_PASS));
        }
    }

    public Connection getConnection() {
        if (freeConnections.isEmpty()) {
            try {
                for (int i = 0; i < MAX_CONNECTION; i++) {
                    freeConnections.add(DriverManager.getConnection(H2_URL, H2_USER, H2_PASS));
                }
            } catch (SQLException e) {
                log.error("Connection cannot be gotten", e);
            }

        }
        return freeConnections.pollLast();
    }

    public void freeConnection(Connection con) throws SQLException {
        if (con != null && !con.isClosed()) {
            freeConnections.add(con);
        }
    }
}