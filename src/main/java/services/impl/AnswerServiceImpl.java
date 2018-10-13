package services.impl;

import dao.AnswerDAO;
import entity.Answer;
import services.api.AnswerService;

import java.util.List;

public class AnswerServiceImpl implements AnswerService {
    private AnswerDAO answerDAO = new AnswerDAO();

    @Override
    public Answer add(Answer answer) {
        return answerDAO.add(answer);
    }

    @Override
    public Answer get(long id) {
        return answerDAO.get(id);
    }

    @Override
    public List<Answer> getAllAnswersByQuestionId(long questionId) {

        return answerDAO.getAllAnswersByQuestionId(questionId);
    }

    @Override
    public void remove(long id) {
        answerDAO.remove(id);
    }

    @Override
    public void removeAllAnswersByQuestionId(long questionId) {
        answerDAO.removeAllAnswersByQuestionId(questionId);
    }

    @Override
    public Boolean updateAnswer(Answer answer, String text, Boolean isRight) {
        Boolean hasRightAnswer = isRight;
        List<Answer> answerList = getAllAnswersByQuestionId(answer.getQuestionId());
        for (Answer a : answerList) {
            if (sameText(answer, text, a)) {
                return false;
            }
            if (a.getRight()) {
                hasRightAnswer = true;
            }
        }
        answerDAO.updateAnswerById(answer.getId(), text, isRight);
        return hasRightAnswer;
    }

    private boolean sameText(Answer answer, String text, Answer a) {
        return a.getText().equals(text) && a.getId() != answer.getId();
    }

    @Override
    public long getAnswerByTextAndQuestionId(String text, long questionId) {
        return answerDAO.getAnswerByTextAndQuestionId(text, questionId);
    }
}
