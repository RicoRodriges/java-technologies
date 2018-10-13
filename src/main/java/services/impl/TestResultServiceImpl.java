package services.impl;

import dao.TestResultDAO;
import entity.*;
import services.api.TestResultService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TestResultServiceImpl implements TestResultService {
    private TestResultDAO testResultDAO = new TestResultDAO();

    @Override
    public void add(TestResult testResult) {
        testResultDAO.add(testResult);
    }

    @Override
    public List<TestResult> getAllTestResultsByUserId(long userId) {
        return testResultDAO.getAllTestResultsByUserId(userId);
    }

    @Override
    public void remove(long id) {
        testResultDAO.remove(id);
    }

    @Override
    public TestResult CheckTest(Test test, Map<Long, List<Long>> answers, User user) {
        TestResult result = null;
        if (test != null && answers != null) {
            int correctAnswers = 0;
            for (Question q : test.getQuest()) {
                if (answers.containsKey(q.getId())) {
                    List<Long> answerIds = answers.get(q.getId());
                    if (CheckQuestion(q, answerIds))
                        correctAnswers++;
                }
            }
            result = new TestResult(user.getId(),
                    test.getId(),
                    correctAnswers,
                    test.getQuest().size(),
                    LocalDateTime.now());
        }
        return result;
    }

    private boolean CheckQuestion(Question q, List<Long> answerIds) {
        boolean isCorrect = true;
        for (Answer a : q.getAnswers()) {
            boolean isChecked = answerIds.contains(a.getId());
            if (a.getRight() && !isChecked || !a.getRight() && isChecked) {
                isCorrect = false;
                break;
            }
        }
        return isCorrect;
    }

    @Override
    public int getScore(TestResult result) {
        return 100 * result.getCorrectAnswers() / result.getCountAnswers();
    }
}
