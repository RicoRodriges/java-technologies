package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Answer implements Serializable {
    @JsonIgnore
    private long id;
    @JsonProperty("Atext")
    private String text;
    @JsonProperty("isRight")
    private Boolean isRight;
    @JsonIgnore
    private long questionId;

    public Answer(String text, Boolean isRight, long questionId) {
        this.text = text;
        this.isRight = isRight;
        this.questionId = questionId;
    }
}
