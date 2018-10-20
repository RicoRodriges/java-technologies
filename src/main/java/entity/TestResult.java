package entity;

import dto.TestResultDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "testId", nullable = false)
    private Test test;
    private Integer correctAnswers;
    private Integer countAnswers;
    private LocalDateTime date;

    public TestResult(User user, Test test, Integer correctAnswers, Integer allAnswers, LocalDateTime date) {
        this.user = user;
        this.test = test;
        this.correctAnswers = correctAnswers;
        this.countAnswers = allAnswers;
        this.date = date;
    }

    public TestResult(TestResultDto testResultDto) {
        id = testResultDto.getId();
        user = new User(testResultDto.getUser());
        test = new Test(testResultDto.getTest());
        correctAnswers = testResultDto.getCorrectAnswers();
        countAnswers = testResultDto.getCountAnswers();
        date = testResultDto.getDate();
    }
}
