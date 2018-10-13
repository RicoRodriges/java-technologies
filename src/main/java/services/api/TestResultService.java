package services.api;

import entity.Test;
import entity.TestResult;
import entity.User;

import java.util.List;
import java.util.Map;

public interface TestResultService {

    void add(TestResult testResult);

    List<TestResult> getAllTestResultsByUserId(long userId);

    void remove(long id);

    TestResult CheckTest(Test test, Map<Long, List<Long>> answers, User user);

    int getScore(TestResult result);

}
