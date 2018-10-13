package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Question implements Serializable {
    @JsonIgnore
    private long id;
    @JsonProperty("Qtext")
    private String text;
    @JsonProperty("answers")
    private List<Answer> answers = new ArrayList<>();
    @JsonIgnore
    private long testId;

    public Question(String text, List<Answer> answers, long testId) {
        this.text = text;
        this.answers = answers;
        this.testId = testId;
    }
}
