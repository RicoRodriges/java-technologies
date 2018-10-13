package entity;

import java.time.LocalDateTime;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getCountAnswers() {
        return countAnswers;
    }

    public void setCountAnswers(int countAnswers) {
        this.countAnswers = countAnswers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
