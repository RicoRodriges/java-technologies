package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AnswerDto implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonProperty("Atext")
    private String text;
    @JsonProperty("isRight")
    private Boolean isRight;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.text = answer.getText();
        this.isRight = answer.getIsRight();
    }
}
