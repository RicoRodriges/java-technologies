package services.impl;

import dao.TestDAO;
import entity.Answer;
import entity.Question;
import entity.Test;
import services.api.AnswerService;
import services.api.QuestionService;
import services.api.TestService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {

    private TestDAO testDAO = new TestDAO();
    private static final QuestionService questionService = new QuestionServiceImpl();
    private static final AnswerService answerService = new AnswerServiceImpl();

    @Override
    public Test addTest(Test test) {
        test.setQuest(questionService.getAllQuestionsByTestId(test.getId()));
        return testDAO.add(test);
    }

    @Override
    public Test getTest(Long id) {
        Test test = testDAO.get(id);
        test.setQuest(questionService.getAllQuestionsByTestId(id));
        return test;
    }

    @Override
    public List<Test> getAllTests() {
        return testDAO.getAll();
    }

    @Override
    public List<Test> getAllTestsByTheme(String theme) {
        return testDAO.getAllByTheme(theme);
    }

    @Override
    public void removeTest(Long id) {
        testDAO.remove(id);
        new QuestionServiceImpl().removeAllQuestionsByTestId(id);
    }

    @Override
    public void updateTest(Test test) {
        testDAO.update(test);
    }

    @Override
    public EditorStatus addTestFromForm(Test test) {
        if (isTestExists(test)) {
            return EditorStatus.TEST_EXISTS;
        } else {
            return validateTestEntry(test);
        }
    }

    @Override
    public EditorStatus editThroughForm(Test test) {
        EditorStatus checkResult = checkTest(test);
        if (checkResult == EditorStatus.OK) {
            long testId = test.getId();
            updateTest(test);
            questionService.removeAllQuestionsByTestId(testId);
            for (Question quest : test.getQuest()) {
                quest.setTestId(testId);
                Question questionWithId = questionService.addEmptyQuestion(quest);
                for (Answer answer : quest.getAnswers()) {
                    answer.setQuestionId(questionWithId.getId());
                    answerService.add(answer);
                }
            }
        }
        return checkResult;
    }

    private EditorStatus validateTestEntry(Test test) {
        if (test.getName().isEmpty()) {
            return EditorStatus.TEST_NO_NAME;
        }
        if (test.getQuest().isEmpty()) {
            return EditorStatus.EMPTY_QUESTIONS;
        }
        Test testWithId = testDAO.add(test);
        for (Question quest : test.getQuest()) {
            EditorStatus x = validateQuestion(testWithId, quest);
            if (x != null) return x;
        }
        return EditorStatus.OK;
    }

    private EditorStatus validateQuestion(Test testWithId, Question quest) {
        if (isQuestionExists(testWithId, quest)) {
            testDAO.remove(testWithId.getId());
            return EditorStatus.QUESTION_EXISTS;
        } else {
            EditorStatus x = validateQuestionEntry(testWithId, quest);
            if (x != null) return x;
        }
        return null;
    }

    private EditorStatus validateQuestionEntry(Test testWithId, Question quest) {
        if (isEmptyQuestionText(quest)) {
            testDAO.remove(testWithId.getId());
            return EditorStatus.QUESTION_NO_TEXT;
        }
        if (quest.getAnswers().isEmpty()) {
            testDAO.remove(testWithId.getId());
            return EditorStatus.QUESTION_NO_ANSWERS;
        }
        quest.setTestId(testWithId.getId());
        Question questionWithId = questionService.addEmptyQuestion(quest);
        for (Answer answer : quest.getAnswers()) {
            EditorStatus x = validateAnswer(testWithId, questionWithId, answer);
            if (x != null) return x;
        }
        return null;
    }

    private EditorStatus validateAnswer(Test testWithId, Question questionWithId, Answer answer) {
        if (isAnswerExists(questionWithId, answer)) {
            testDAO.remove(testWithId.getId());
            return EditorStatus.ANSWER_EXISTS;
        } else {
            if (validateAnswerEntry(testWithId, questionWithId, answer)) return EditorStatus.ANSWER_NO_TEXT;
        }
        return null;
    }

    private boolean validateAnswerEntry(Test testWithId, Question questionWithId, Answer answer) {
        if (isEmptyAnswerText(answer)) {
            testDAO.remove(testWithId.getId());
            return true;
        }
        answer.setQuestionId(questionWithId.getId());
        answerService.add(answer);
        return false;
    }

    private boolean isTestExists(Test test) {
        return testDAO.getTestsIdByNameAndType(test.getName(), test.getType()) != -1;
    }

    private boolean isQuestionExists(Test testWithId, Question quest) {
        return questionService.getQuestionsIdByTextAndTestId(quest.getText(), testWithId.getId()) != -1;
    }

    private boolean isEmptyQuestionText(Question quest) {
        return quest.getText() == null || quest.getText().isEmpty();
    }

    private boolean isAnswerExists(Question questionWithId, Answer answer) {
        return answerService.getAnswerByTextAndQuestionId(answer.getText(), questionWithId.getId()) != -1;
    }

    private boolean isEmptyAnswerText(Answer answer) {
        return answer.getText() == null || answer.getText().isEmpty();
    }

    private EditorStatus checkTest(Test test) {
        if (test.getName().isEmpty()) {
            return EditorStatus.TEST_NO_NAME;
        }
        if (test.getQuest().isEmpty()) {
            return EditorStatus.EMPTY_QUESTIONS;
        }

        if (hasDuplicateQuestions(test)) {
            return EditorStatus.QUESTION_EXISTS;
        }

        for (Question quest : test.getQuest()) {

            if (quest.getText().isEmpty()) {
                return EditorStatus.QUESTION_NO_TEXT;
            }
            if (quest.getAnswers().isEmpty()) {
                return EditorStatus.QUESTION_NO_ANSWERS;
            } else {
                if (hasDuplicateAnswers(quest)) {
                    return EditorStatus.ANSWER_EXISTS;
                }
                for (Answer answer : quest.getAnswers()) {
                    if (answer.getText().isEmpty()) {
                        return EditorStatus.ANSWER_NO_TEXT;
                    }
                }
            }
        }
        return EditorStatus.OK;
    }

    private boolean hasDuplicateAnswers(Question quest) {
        List<String> answerTexts = quest.getAnswers().stream().map(Answer::getText).collect(Collectors.toList());
        for (String texts : answerTexts) {
            if (Collections.frequency(answerTexts, texts) > 1) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDuplicateQuestions(Test test) {
        List<String> questionsTexts = test.getQuest().stream().map(Question::getText).collect(Collectors.toList());
        for (String texts : questionsTexts) {
            if (Collections.frequency(questionsTexts, texts) > 1) {
                return true;
            }
        }
        return false;
    }

}
