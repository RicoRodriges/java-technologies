package entity;

import dto.QuestionDto;
import dto.TestDto;
import dto.TestTypes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "testId", nullable = false)
    private List<Question> quest = new ArrayList<>();
    private TestTypes type;
    private LocalDate creationDate;

    public Test(String name, List<Question> quest, TestTypes type) {
        this.name = name;
        this.quest = quest;
        this.type = type;
    }

    public Test(TestDto testDto) {
        id = testDto.getId();
        name = testDto.getName();
        type = testDto.getType();
        creationDate = testDto.getCreationDate();
        for (QuestionDto questionDto : testDto.getQuest()) {
            quest.add(new Question(questionDto));
        }
    }
}
