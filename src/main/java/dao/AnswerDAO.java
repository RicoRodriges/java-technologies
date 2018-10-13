package dao;

import config.ConnectionPool;
import entity.Answer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO extends AbstractDAO<Answer, Long> {

    private final static Logger log = LogManager.getLogger(AnswerDAO.class);

    @Override
    public Answer add(Answer answer) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("ADD_ANSWER"))
        ) {
            setSQLParameters(answer, st);
            st.executeUpdate();
            log.info("Answer " + answer + " was added");

            return get(answer.getText(), answer.getQuestionId());
        } catch (SQLException e) {
            log.error("Answer " + answer + " wasn't added", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    private Answer get(String text, long questionId) {
        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ANSWER_BY_TEXT_AND_QUESTION_ID"))
        ) {
            st.setString(1, text);
            st.setLong(2, questionId);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return getAnswerByTextAndQuestionId(text, questionId, rs);
                }
            } catch (SQLException e) {
                log.error("Answer(text:" + text + ", questionId:" + questionId + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Answer(text:" + text + ", questionId:" + questionId + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return null;
    }

    @Override
    public Answer get(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ANSWER"))
        ) {
            st.setLong(1, id);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return getAnswerById(id, rs);
                }
            } catch (SQLException e) {
                log.error("Answer(id:" + id + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Answer(id:" + id + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return null;
    }

    public long getAnswerByTextAndQuestionId(String text, long questionId) {
        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ANSWER_BY_TEXT_AND_QUESTION_ID"))
        ) {
            st.setString(1, text);
            st.setLong(2, questionId);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                log.error("Question(text: " + text + ", questionId: " + questionId + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("Question(text: " + text + ", questionId: " + questionId + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
    }

    public List<Answer> getAllAnswersByQuestionId(Long questionId) {

        Connection con = pool.getConnection();
        List<Answer> answerList = new ArrayList<>();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("GET_ALL_ANSWERS_BY_QUESTION_ID"))
        ) {
            st.setLong(1, questionId);
            try (
                    ResultSet rs = st.executeQuery()
            ) {
                while (rs.next()) {
                    Answer a = getAnswerByQuestionId(questionId, rs);
                    answerList.add(a);
                }
            } catch (SQLException e) {
                log.error("All answers for question(id:" + questionId + ") cannot be gotten", e);
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            log.error("All answers for question(id:" + questionId + ") cannot be gotten", e);
            throw new RuntimeException(e);
        } finally {
            freeCon(con);
        }
        return answerList;
    }

    @Override
    public void remove(Long id) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("REMOVE_ANSWER"))
        ) {
            st.setLong(1, id);
            st.executeUpdate();
            log.info("Answer(id:" + id + ") was removed");
        } catch (SQLException e) {
            log.error("Answer(id:" + id + ") wasn't removed", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    public void removeAllAnswersByQuestionId(Long questionId) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("REMOVE_ALL_ANSWERS_BY_QUESTION_ID"))
        ) {
            st.setLong(1, questionId);
            st.executeUpdate();
            log.info("All answers for question(id:" + questionId + ") were removed");
        } catch (SQLException e) {
            log.error("All answers for question(id:" + questionId + ") weren't removed", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    public void updateAnswerById(long id, String text, Boolean isRight) {

        Connection con = pool.getConnection();

        try (
                PreparedStatement st = con.prepareStatement(sqlQueries.getString("UPDATE_ANSWER_BY_ID"))
        ) {
            setSQLParametersForUpdate(id, text, isRight, st);
            st.executeUpdate();
            log.info("Answer(id:" + id + ") was updated");
        } catch (SQLException e) {
            log.error("Answer(id:" + id + ") wasn't updated", e);
            throw new RuntimeException();
        } finally {
            freeCon(con);
        }
    }

    private void freeCon(Connection con) {
        try {
            ConnectionPool.freeConnection(con);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private void setSQLParametersForUpdate(long id, String text, Boolean isRight, PreparedStatement st) throws SQLException {
        st.setString(1, text);
        st.setBoolean(2, isRight);
        st.setLong(3, id);
    }

    private void setSQLParameters(Answer answer, PreparedStatement st) throws SQLException {
        st.setString(1, answer.getText());
        st.setBoolean(2, answer.getIsRight());
        st.setLong(3, answer.getQuestionId());
    }

    private Answer getAnswerById(Long id, ResultSet rs) throws SQLException {
        String text = rs.getString("text");
        boolean isRight = rs.getBoolean("isRight");
        long questionId = rs.getLong("questionId");
        Answer a = new Answer(text, isRight, questionId);
        a.setId(id);
        return a;
    }

    private Answer getAnswerByQuestionId(Long questionId, ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String text = rs.getString("text");
        boolean isRight = rs.getBoolean("isRight");
        Answer a = new Answer(text, isRight, questionId);
        a.setId(id);
        return a;
    }

    private Answer getAnswerByTextAndQuestionId(String text, Long questionId, ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        boolean isRight = rs.getBoolean("isRight");
        Answer a = new Answer(text, isRight, questionId);
        a.setId(id);
        return a;
    }
}
