package dao;

import entity.Answer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnswerDAO extends AbstractDAO<Answer, Long> {

    private final static Logger log = LogManager.getLogger(AnswerDAO.class);

    public AnswerDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Answer add(Answer answer) {
        jdbcTemplate.update(sqlQueries.getString("ADD_ANSWER"), answer.getText(), answer.getIsRight(), answer.getQuestionId());
        log.info("Answer " + answer + " was added");
        return get(answer.getText(), answer.getQuestionId());
    }

    private Answer get(String text, long questionId) {
        RowMapper<Answer> map = ((rs, rowNum) -> getAnswerByTextAndQuestionId(text, questionId, rs));
        List<Answer> answers = jdbcTemplate.query(sqlQueries.getString("GET_ANSWER_BY_TEXT_AND_QUESTION_ID"), map, text, questionId);
        if (answers.isEmpty()) {
            return null;
        } else {
            return answers.get(0);
        }
    }

    @Override
    public Answer get(Long id) {
        RowMapper<Answer> map = ((rs, rowNum) -> getAnswerById(id, rs));
        List<Answer> answers = jdbcTemplate.query(sqlQueries.getString("GET_ANSWER"), map, id);
        if (answers.isEmpty()) {
            return null;
        } else {
            return answers.get(0);
        }
    }

    public long getAnswerByTextAndQuestionId(String text, long questionId) {
        Answer answer = get(text, questionId);
        return (answer == null) ? -1 : answer.getId();
    }

    public List<Answer> getAllAnswersByQuestionId(Long questionId) {
        RowMapper<Answer> map = ((rs, rowNum) -> getAnswerByQuestionId(questionId, rs));
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_ANSWERS_BY_QUESTION_ID"), map, questionId);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_ANSWER"), id);
        log.info("Answer(id:" + id + ") was removed");
    }

    public void removeAllAnswersByQuestionId(Long questionId) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_ALL_ANSWERS_BY_QUESTION_ID"), questionId);
        log.info("All answers for question(id:" + questionId + ") were removed");
    }

    public void updateAnswerById(long id, String text, Boolean isRight) {
        jdbcTemplate.update(sqlQueries.getString("UPDATE_ANSWER_BY_ID"), text, isRight, id);
        log.info("Answer(id:" + id + ") was updated");
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
