package dao;

import entity.Question;
import entity.Test;
import entity.TestTypes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TestDAO extends AbstractDAO<Test, Long> {

    private final static Logger log = LogManager.getLogger(TestDAO.class);
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String TYPE = "type";
    private final static String DATE = "creationDate";

    private final QuestionDAO questionDAO;

    public TestDAO(JdbcTemplate jdbcTemplate, QuestionDAO questionDAO) {
        super(jdbcTemplate);
        this.questionDAO = questionDAO;
    }

    @Override
    public Test add(Test test) {
        jdbcTemplate.update(sqlQueries.getString("ADD_TEST"), test.getName(), test.getType().getName(), Date.valueOf(LocalDate.now()));
        log.info("Test " + test + " was added");
        test.setId(getTestsIdByNameAndType(test.getName(), test.getType()));
        return test;
    }

    public long getTestsIdByNameAndType(String name, TestTypes type) {
        RowMapper<Test> map = (rs, rowNum) -> {
            Test test = new Test();
            test.setId(rs.getLong(1));
            return test;
        };
        List<Test> tests = jdbcTemplate.query(sqlQueries.getString("GET_TEST_BY_NAME_AND_TYPE"), map, name, type.getName());
        if (tests.isEmpty()) {
            return -1;
        } else {
            return tests.get(0).getId();
        }
    }

    public List<Test> getAllByTheme(String theme) {
        RowMapper<Test> map = (rs, rowNum) -> getTestById(rs.getLong("id"), rs);
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_TESTS_BY_THEME"), map, theme);
    }

    @Override
    public Test get(Long id) {
        RowMapper<Test> map = (rs, rowNum) -> getTestById(id, rs);
        List<Test> tests = jdbcTemplate.query(sqlQueries.getString("GET_TEST"), map, id);
        if (tests.isEmpty()) {
            return null;
        } else {
            return tests.get(0);
        }
    }

    public List<Test> getAll() {
        RowMapper<Test> map = (rs, rowNum) -> getTestById(rs.getLong(ID), rs);
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_TESTS"), map);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_TEST"), id);
        log.info("Test(id:" + id + ") was removed");
    }

    public void update(Test test) {
        jdbcTemplate.update(sqlQueries.getString("UPDATE_TEST"), test.getName(), test.getType().getName(), test.getId());
        log.info("Test " + test + " was updated");
    }

    private Test getTestById(Long id, ResultSet rs) throws SQLException {
        String name = rs.getString(NAME);
        TestTypes type = TestTypes.getType(rs.getString(TYPE));
        Date creationDate = rs.getDate(DATE);

        List<Question> questionList = questionDAO.getAllQuestionsByTestId(id);

        Test test = new Test(name, questionList, type);
        test.setId(id);
        if (creationDate != null) {
            test.setCreationDate(creationDate.toLocalDate());
        }
        return test;
    }
}
