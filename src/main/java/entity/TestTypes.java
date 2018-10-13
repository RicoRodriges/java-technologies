package entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestTypes {
    @JsonProperty("Math")
    MATH("Math"),
    @JsonProperty("Physics")
    PHYSICS("Physics"),
    @JsonProperty("Russian")
    RUSSIAN("Russian"),
    @JsonProperty("English")
    ENGLISH("English");

    private String name;

    @JsonCreator
    public static TestTypes getType(String name) {
        for (TestTypes type : TestTypes.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
