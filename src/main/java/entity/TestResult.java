package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long testId;
    private Integer correctAnswers;
    private Integer countAnswers;
    private LocalDateTime date;

    public TestResult(Long userId, Long testId, Integer correctAnswers, Integer allAnswers, LocalDateTime date) {
        this.userId = userId;
        this.testId = testId;
        this.correctAnswers = correctAnswers;
        this.countAnswers = allAnswers;
        this.date = date;
    }
}
