package entity;

import dto.AnswerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean isRight;

    public Answer(String text, Boolean isRight) {
        this.text = text;
        this.isRight = isRight;
    }

    public Answer(AnswerDto answerDto) {
        id = answerDto.getId();
        text = answerDto.getText();
        isRight = answerDto.getIsRight();
    }
}
