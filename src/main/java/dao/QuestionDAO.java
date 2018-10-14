package dao;

import config.ConnectionPool;
import entity.Answer;
import entity.Question;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDAO extends AbstractDAO<Question, Long> {

    private final static Logger log = LogManager.getLogger(QuestionDAO.class);

    public QuestionDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public Question add(Question question) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("ADD_QUESTION"))
        ) {

            setSQLParameters(st, question.getText(), question.getTestId());
            st.executeUpdate();
            log.info("Question " + question + " was added");
            question.setId(getQuestionsIdByTextAndTestId(question.getText(), question.getTestId()));
            return question;
        } catch (SQLException e) {
            log.error("Question " + question + " wasn't added", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    public long getQuestionsIdByTextAndTestId(String text, long testId) {
        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_QUESTION_BY_TEXT_AND_TEST_ID"))
        ) {
            st.setString(1, text);
            st.setLong(2, testId);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                log.error("Question(text: " + text + ", testId: " + testId + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Question(text: " + text + ", testId: " + testId + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }


    @Override
    public Question get(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_QUESTION"))
        ) {
            st.setLong(1, id);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return getQuestionById(id, rs);
                }
            } catch (SQLException e) {
                log.error("Question(id:" + id + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Question(id:" + id + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return null;
    }


    public List<Question> getAllQuestionsByTestId(Long testId) {

        List<Question> questionList = new ArrayList<>();
        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ALL_QUESTIONS_BY_TEST_ID"))
        ) {
            st.setLong(1, testId);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                while (rs.next()) {
                    questionList.add(get(rs.getLong("Id")));
                }
            } catch (SQLException e) {
                log.error("All questions for test(id:" + testId + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("All questions for test(id:" + testId + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return questionList;
    }

    @Override
    public void remove(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("REMOVE_QUESTION"))
        ) {
            st.setLong(1, id);
            st.executeUpdate();
            log.info("Question(id:" + id + ") was removed");
        } catch (SQLException e) {
            log.error("Question(id:" + id + ") wasn't removed", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    public void updateTextById(long id, String text) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("UPDATE_QUESTION_BY_ID"))
        ) {
            setSQLParameters(st, text, id);
            st.executeUpdate();
            log.info("Question(id:" + id + ") was updated");
        } catch (SQLException e) {
            log.error("Question(id:" + id + ") wasn't updated", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    private void setSQLParameters(PreparedStatement st, String text, long testId) throws SQLException {
        st.setString(1, text);
        st.setLong(2, testId);
    }

    private Question getQuestionById(Long id, ResultSet rs) throws SQLException {
        String text = rs.getString("questions.text");
        long testId = rs.getLong("questions.testId");
        List<Answer> answers = new ArrayList<>();
        do {
            long answerId = rs.getLong("answers.id");
            String answerText = rs.getString("answers.text");
            boolean isRight = rs.getBoolean("answers.isRight");
            Answer a = new Answer(answerText, isRight, id);
            a.setId(answerId);
            answers.add(a);
        } while (rs.next());

        Question q = new Question(text, answers, testId);
        q.setId(id);
        return q;
    }

    private void freeCon(Connection con) {
        try {
            pool.freeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
