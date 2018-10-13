package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {
    @JsonIgnore
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("quest")
    private List<Question> quest;
    @JsonProperty("type")
    private TestTypes type;
    private Date creationDate;

    public Test(String name, List<Question> quest, TestTypes type) {
        this.name = name;
        this.quest = quest;
        this.type = type;
    }

    public Test() {
        quest = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuest() {
        return quest;
    }

    public void setQuest(List<Question> quest) {
        this.quest = quest;
    }

    public TestTypes getType() {
        return type;
    }

    public void setType(TestTypes type) {
        this.type = type;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setTestIdForQuest(Long testId) {
        quest.forEach(a -> a.setTestId(testId));
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", quest=" + quest +
                ", type=" + type +
                '}';
    }
}
