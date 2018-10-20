package services.impl;

import dao.TestResultDAO;
import dto.*;
import entity.TestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.api.TestResultService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultServiceImpl implements TestResultService {

    private final TestResultDAO testResultDAO;

    @Override
    public void add(TestResultDto testResult) {
        testResultDAO.save(new TestResult(testResult));
    }

    @Override
    public List<TestResultDto> getAllTestResultsByUserId(Long userId) {
        List<TestResult> testResults = testResultDAO.findAllByUserId(userId);
        ArrayList<TestResultDto> testResultDtos = new ArrayList<>(testResults.size());
        for (TestResult testResult : testResults) {
            testResultDtos.add(new TestResultDto(testResult));
        }
        return testResultDtos;
    }

    @Override
    public TestResultDto CheckTest(TestDto test, Map<Long, List<Long>> answers, UserDto user) {
        TestResultDto result = null;
        if (test != null && answers != null) {
            int correctAnswers = 0;
            for (QuestionDto q : test.getQuest()) {
                if (answers.containsKey(q.getId())) {
                    List<Long> answerIds = answers.get(q.getId());
                    if (CheckQuestion(q, answerIds))
                        correctAnswers++;
                }
            }
            result = new TestResultDto(null,
                    user,
                    test,
                    correctAnswers,
                    test.getQuest().size(),
                    LocalDateTime.now());
        }
        return result;
    }

    private boolean CheckQuestion(QuestionDto q, List<Long> answerIds) {
        boolean isCorrect = true;
        for (AnswerDto a : q.getAnswers()) {
            boolean isChecked = answerIds.contains(a.getId());
            if (a.getIsRight() && !isChecked || !a.getIsRight() && isChecked) {
                isCorrect = false;
                break;
            }
        }
        return isCorrect;
    }

    @Override
    public int getScore(TestResultDto result) {
        return 100 * result.getCorrectAnswers() / result.getCountAnswers();
    }
}
