package dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum TestTypes implements Serializable {
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
