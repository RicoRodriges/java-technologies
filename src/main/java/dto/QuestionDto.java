package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Answer;
import entity.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDto implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonProperty("Qtext")
    private String text;
    @JsonProperty("answers")
    private List<AnswerDto> answers = new ArrayList<>();

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        for (Answer a : question.getAnswers()) {
            this.answers.add(new AnswerDto(a));
        }
    }
}
