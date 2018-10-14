package dao;

import entity.Answer;
import entity.Question;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDAO extends AbstractDAO<Question, Long> {

    private final static Logger log = LogManager.getLogger(QuestionDAO.class);

    public QuestionDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Question add(Question question) {
        jdbcTemplate.update(sqlQueries.getString("ADD_QUESTION"), question.getText(), question.getTestId());
        log.info("Question " + question + " was added");
        question.setId(getQuestionsIdByTextAndTestId(question.getText(), question.getTestId()));
        return question;
    }

    public long getQuestionsIdByTextAndTestId(String text, long testId) {
        RowMapper<Question> map = (rs, rowNum) -> {
            Question question = new Question();
            question.setId(rs.getLong(1));
            return question;
        };
        List<Question> questions = jdbcTemplate.query(sqlQueries.getString("GET_QUESTION_BY_TEXT_AND_TEST_ID"), map, text, testId);
        if (questions.isEmpty()) {
            return -1;
        } else {
            return questions.get(0).getId();
        }
    }


    @Override
    public Question get(Long id) {
        RowMapper<Question> map = (rs, rowNum) -> getQuestionById(id, rs);
        List<Question> questions = jdbcTemplate.query(sqlQueries.getString("GET_QUESTION"), map, id);
        if (questions.isEmpty()) {
            return null;
        } else {
            return questions.get(0);
        }
    }


    public List<Question> getAllQuestionsByTestId(Long testId) {
        RowMapper<Question> map = (rs, rowNum) -> get(rs.getLong("Id"));
        return jdbcTemplate.query(sqlQueries.getString("GET_ALL_QUESTIONS_BY_TEST_ID"), map, testId);
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(sqlQueries.getString("REMOVE_QUESTION"), id);
        log.info("Question(id:" + id + ") was removed");
    }

    public void updateTextById(long id, String text) {
        jdbcTemplate.update(sqlQueries.getString("UPDATE_QUESTION_BY_ID"), text, id);
        log.info("Question(id:" + id + ") was updated");
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
}
