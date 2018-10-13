package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Test implements Serializable {
    @JsonIgnore
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quest")
    private List<Question> quest = new ArrayList<>();
    @JsonProperty("type")
    private TestTypes type;
    private LocalDate creationDate;

    public Test(String name, List<Question> quest, TestTypes type) {
        this.name = name;
        this.quest = quest;
        this.type = type;
    }
}
