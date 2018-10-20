package dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.Question;
import entity.Test;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TestDto implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quest")
    private List<QuestionDto> quest = new ArrayList<>();
    @JsonProperty("type")
    private TestTypes type;
    private LocalDate creationDate;

    public TestDto(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.type = test.getType();
        this.creationDate = test.getCreationDate();
        for (Question q : test.getQuest()) {
            this.quest.add(new QuestionDto(q));
        }
    }
}
