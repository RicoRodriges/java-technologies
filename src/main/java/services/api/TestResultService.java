package services.api;

import dto.TestDto;
import dto.TestResultDto;
import dto.UserDto;

import java.util.List;
import java.util.Map;

public interface TestResultService {

    void add(TestResultDto testResult);

    List<TestResultDto> getAllTestResultsByUserId(Long userId);

    TestResultDto CheckTest(TestDto test, Map<Long, List<Long>> answers, UserDto user);

    int getScore(TestResultDto result);

}
