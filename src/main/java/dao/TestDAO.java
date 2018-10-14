package dao;

import config.ConnectionPool;
import entity.Question;
import entity.Test;
import entity.TestTypes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestDAO extends AbstractDAO<Test, Long> {

    private final static Logger log = LogManager.getLogger(TestDAO.class);
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String TYPE = "type";
    private final static String DATE = "creationDate";

    private final QuestionDAO questionDAO;

    public TestDAO(ConnectionPool pool, QuestionDAO questionDAO) {
        super(pool);
        this.questionDAO = questionDAO;
    }

    @Override
    public Test add(Test test) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("ADD_TEST"))
        ) {
            setSQLParameters(test, st);
            st.executeUpdate();
            log.info("Test " + test + " was added");

            test.setId(getTestsIdByNameAndType(test.getName(), test.getType()));
            return test;
        } catch (SQLException e) {
            log.error("Test " + test + " wasn't added", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    public long getTestsIdByNameAndType(String name, TestTypes type) {
        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_TEST_BY_NAME_AND_TYPE"))
        ) {
            st.setString(1, name);
            st.setString(2, type.getName());
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                log.error("Test(name: " + name + ", type: " + type + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Test(name: " + name + ", type: " + type + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    public List<Test> getAllByTheme(String theme) {

        Connection con = pool.getConnection();
        List<Test> testList = new ArrayList<>();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ALL_TESTS_BY_THEME"))
        ) {
            st.setString(1, theme);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                while (rs.next()) {
                    long testId = rs.getLong("id");
                    Test test = getTestById(testId, rs);
                    testList.add(test);
                }
            } catch (SQLException e) {
                log.error("Test(theme:" + theme + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            log.error("Test(theme:" + theme + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return testList;
    }

    @Override
    public Test get(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_TEST"))
        ) {
            st.setLong(1, id);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return getTestById(id, rs);
                } else {
                    return null;
                }
            } catch (SQLException e) {
                log.error("Test(id:" + id + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Test(id:" + id + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    public List<Test> getAll() {

        Connection con = pool.getConnection();
        List<Test> testList = new ArrayList<>();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ALL_TESTS"));
                ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                long testId = rs.getLong(ID);
                Test test = getTestById(testId, rs);
                testList.add(test);
            }
        } catch (SQLException e) {
            log.error("All tests cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return testList;
    }

    @Override
    public void remove(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("REMOVE_TEST"))
        ) {
            st.setLong(1, id);
            st.executeUpdate();
            log.info("Test(id:" + id + ") was removed");
        } catch (SQLException e) {
            log.error("Test(id:" + id + ") wasn't removed", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    public void update(Test test) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("UPDATE_TEST"))
        ) {
            setSQLParameters(test, st);
            st.setLong(3, test.getId());
            st.executeUpdate();
            log.info("Test " + test + " was updated");
        } catch (SQLException e) {
            log.error("Test " + test + " wasn't updated", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    private void freeCon(Connection con) {
        try {
            pool.freeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void setSQLParameters(Test test, PreparedStatement st) throws SQLException {
        st.setString(1, test.getName());
        st.setString(2, test.getType().getName());
        st.setDate(3, Date.valueOf(LocalDate.now()));
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
