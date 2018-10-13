package entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestResult {
    private long id;
    private long userId;
    private long testId;
    private int correctAnswers;
    private int countAnswers;
    private LocalDateTime date;

    public TestResult(long userId, long testId, int correctAnswers, int allAnswers, LocalDateTime date) {
        this.userId = userId;
        this.testId = testId;
        this.correctAnswers = correctAnswers;
        this.countAnswers = allAnswers;
        this.date = date;
    }
}
