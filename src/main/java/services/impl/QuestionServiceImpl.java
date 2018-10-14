package services.impl;

import dao.QuestionDAO;
import entity.Answer;
import entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import services.api.AnswerService;
import services.api.QuestionService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDAO questionDAO;
    private final AnswerService answerService;

    @Override
    public Question add(Question question) {
        Question quest = questionDAO.add(question);
        for (Answer a : question.getAnswers()) {
            answerService.add(a);
        }
        return quest;
    }

    @Override
    public Question get(long id) {
        return questionDAO.get(id);
    }

    @Override
    public List<Question> getAllQuestionsByTestId(long testId) {
        return questionDAO.getAllQuestionsByTestId(testId);
    }

    @Override
    public void remove(long id) {
        questionDAO.remove(id);
        answerService.removeAllAnswersByQuestionId(id);
    }

    @Override
    public void removeAllQuestionsByTestId(long testId) {
        List<Question> questionList = questionDAO.getAllQuestionsByTestId(testId);
        for (Question question : questionList) {
            questionDAO.remove(question.getId());
        }
    }

    @Override
    public Boolean updateText(String text, Question question) {
        List<Question> questionList = getAllQuestionsByTestId(question.getTestId());
        for (Question q : questionList) {
            if (q.getText().equals(text)) {
                return false;
            }
        }

        questionDAO.updateTextById(question.getId(), text);
        return true;
    }


    @Override
    public Question addEmptyQuestion(Question question) {
        return questionDAO.add(question);
    }


    @Override
    public long getQuestionsIdByTextAndTestId(String text, long testId) {
        return questionDAO.getQuestionsIdByTextAndTestId(text, testId);
    }
}
