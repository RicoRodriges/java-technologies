package config;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final static Logger log = LogManager.getLogger(InitDB.class);
    private static final String SQL_QUERIES = "sql_queries";
    private static final String H2_CREATE_USER = "h2.create.user";
    private static final String H2_CREATE_TEST = "h2.create.test";
    private static final String H2_CREATE_QUESTION = "h2.create.question";
    private static final String H2_CREATE_ANSWER = "h2.create.answer";
    private static final String H2_CREATE_TESTRESULT = "h2.create.testresult";
    private static final String H2_CREATE_USER_TUTOR = "h2.create.user.tutor";

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        ResourceBundle res = ResourceBundle.getBundle(SQL_QUERIES);
        String[] sqls = {res.getString(H2_CREATE_USER),
                res.getString(H2_CREATE_TEST),
                res.getString(H2_CREATE_QUESTION),
                res.getString(H2_CREATE_ANSWER),
                res.getString(H2_CREATE_TESTRESULT),
                res.getString(H2_CREATE_USER_TUTOR)};
        for (String sql : sqls) {
            jdbcTemplate.execute(sql);
        }
        log.info("Database successfully initialized");
    }
}
