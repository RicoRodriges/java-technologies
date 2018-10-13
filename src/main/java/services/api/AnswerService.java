package services.api;

import entity.Answer;

import java.util.List;

public interface AnswerService {

    Answer add(Answer answer);

    Answer get(long id);

    List<Answer> getAllAnswersByQuestionId(long questionId);

    void remove(long id);

    void removeAllAnswersByQuestionId(long questionId);

    Boolean updateAnswer(Answer answer, String text, Boolean isRight);

    long getAnswerByTextAndQuestionId(String text, long questionId);
}
