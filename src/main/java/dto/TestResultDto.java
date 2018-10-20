package dto;

import entity.TestResult;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TestResultDto implements Serializable {
    private Long id;
    private UserDto user;
    private TestDto test;
    private Integer correctAnswers;
    private Integer countAnswers;
    private LocalDateTime date;

    public TestResultDto(TestResult testResult) {
        this.id = testResult.getId();
        this.user = new UserDto(testResult.getUser());
        this.test = new TestDto(testResult.getTest());
        this.correctAnswers = testResult.getCorrectAnswers();
        this.countAnswers = testResult.getCountAnswers();
        this.date = testResult.getDate();
    }
}
