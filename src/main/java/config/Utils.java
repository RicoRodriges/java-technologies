package config;

import entity.TestResult;
import org.springframework.stereotype.Component;
import services.api.TestResultService;
import services.api.TestService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Utils {
    private static TestService testService;
    private static TestResultService testResultService;

    Utils(TestService testService, TestResultService testResultService) {
        Utils.testService = testService;
        Utils.testResultService = testResultService;
    }

    public static String getTestName(long testId) {
        return testService.getTest(testId).getName();
    }

    public static int getPercents(TestResult testResult) {
        return testResultService.getScore(testResult);
    }

    public static String dateFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLL.yyyy HH:mm");
        return localDateTime.format(formatter);
    }
}
