package services.api;

import entity.Question;

import java.util.List;

public interface QuestionService {
    Question add(Question question);

    Question get(long id);

    List<Question> getAllQuestionsByTestId(long testId);

    void remove(long id);

    void removeAllQuestionsByTestId(long testId);

    Boolean updateText(String text, Question question);

    Question addEmptyQuestion(Question question);

    long getQuestionsIdByTextAndTestId(String text, long testId);
}
