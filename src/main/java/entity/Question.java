package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    @JsonIgnore
    private long id;
    @JsonProperty("Qtext")
    private String text;
    @JsonProperty("answers")
    private List<Answer> answers;
    @JsonIgnore
    private long testId;

    public Question(String text, List<Answer> answers, long testId) {
        this.text = text;
        this.answers = answers;
        this.testId = testId;
    }

    public Question() {
        answers = new ArrayList<>();
    }

    public long getTestId() {

        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                '}';
    }
}
