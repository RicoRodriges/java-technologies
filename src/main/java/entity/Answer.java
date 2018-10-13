package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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

    public Answer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                '}';
    }
}
