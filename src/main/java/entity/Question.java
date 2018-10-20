package entity;

import dto.AnswerDto;
import dto.QuestionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "questionId", nullable = false)
    private List<Answer> answers = new ArrayList<>();

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public Question(QuestionDto questionDto) {
        id = questionDto.getId();
        text = questionDto.getText();
        for (AnswerDto answerDto : questionDto.getAnswers()) {
            answers.add(new Answer(answerDto));
        }
    }
}
