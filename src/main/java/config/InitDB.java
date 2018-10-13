package config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class InitDB extends HttpServlet {

    private final static Logger log = LogManager.getLogger(InitDB.class);
    private static final String SQL_QUERIES = "sql_queries";
    private static final String H2_CREATE_USER = "h2.create.user";
    private static final String H2_CREATE_TEST = "h2.create.test";
    private static final String H2_CREATE_QUESTION = "h2.create.question";
    private static final String H2_CREATE_ANSWER = "h2.create.answer";
    private static final String H2_CREATE_TESTRESULT = "h2.create.testresult";
    private static final String H2_CREATE_USER_TUTOR = "h2.create.user.tutor";

    @Override
    public void init() throws ServletException {
        super.init();
        Statement st = null;
        Connection con = null;
        try {
            ConnectionPool pool = ConnectionPool.getInstance();
            con = pool.getConnection();
            ResourceBundle res = ResourceBundle.getBundle(SQL_QUERIES);
            String[] sqls = {res.getString(H2_CREATE_USER),
                    res.getString(H2_CREATE_TEST),
                    res.getString(H2_CREATE_QUESTION),
                    res.getString(H2_CREATE_ANSWER),
                    res.getString(H2_CREATE_TESTRESULT),
                    res.getString(H2_CREATE_USER_TUTOR)};
            st = con.createStatement();
            for (String sql : sqls) {
                st.executeUpdate(sql);
            }
            log.info("Database successfully initialized");
        } catch (SQLException e) {
            log.error("Database didn't initialize", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (st != null)
                    st.close();
                if (con != null)
                    ConnectionPool.freeConnection(con);
            } catch (SQLException e) {
                log.error("Database didn't initialize", e);
            }
        }
    }
}
