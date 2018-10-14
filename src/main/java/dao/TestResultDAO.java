package dao;

import entity.TestResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class TestResultDAO extends AbstractDAO<TestResult, Long> {

    private final static Logger log = LogManager.getLogger(TestResultDAO.class);
    private final static String ID = "id";
    private final static String TESTID = "testId";
    private final static String USERID = "userId";
    private final static String CORRECTANSWERS = "correctAnswers";
    private final static String COUNTANSWERS = "countAnswers";
    private final static String DATE = "date";

    public TestResultDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public TestResult add(TestResult testResult) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(sqlQueries.getString("ADD_TESTRESULT"), Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, testResult.getUserId());
                    ps.setLong(2, testResult.getTestId());
                    ps.setInt(3, testResult.getCorrectAnswers());
                    ps.setInt(4, testResult.getCountAnswers());
                    ps.setObject(5, testResult.getDate());
                    return ps;
                },
                keyHolder);
        testResult.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return testResult;
    }

    @Override
    public TestResult get(Long id) {
        RowMapper<TestResult> map = (rs, rowNum) -> getTestResult(rs);
        List<TestResult> testResults = jdbcTemplate.query(sqlQueries.getString("GET_TESTRESULT"), map, id);
        if (testResults.isEmpty()) {
            return null;
        } else {
            return testResults.get(0);
        }
    }

    public List<TestResult> get(long userId, long testId) {
        RowMapper<TestResult> map = (rs, rowNum) -> getTestResult(rs);
        return jdbcTemplate.query(sqlQueries.getString("GET_TESTRESULT_BY_USER_AND_TEST"), map, userId, testId);
    }

    public List<TestResult> getAllTestResultsByUserId(long userId) {
        RowMapper<TestResult> map = (rs, rowNum) -> getTestResult(rs);
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_TESTRESULTS_BY_USER"), map, userId);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_TESTRESULT"), id);
        log.info("TestResult(id:" + id + ") was removed");
    }

    private TestResult getTestResult(ResultSet rs) throws SQLException {
        TestResult testResult = new TestResult(rs.getLong(USERID), rs.getLong(TESTID), rs.getInt(CORRECTANSWERS), rs.getInt(COUNTANSWERS), rs.getObject(DATE, LocalDateTime.class));
        testResult.setId(rs.getLong(ID));
        return testResult;
    }
}