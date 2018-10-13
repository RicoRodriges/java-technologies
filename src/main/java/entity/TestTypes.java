package entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    TestTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static TestTypes getType(String name) {
        switch (name) {
            case "Math": return TestTypes.MATH;
            case "Physics": return TestTypes.PHYSICS;
            case "Russian": return TestTypes.RUSSIAN;
            case "English": return TestTypes.ENGLISH;
        }
        return null;
    }
}
