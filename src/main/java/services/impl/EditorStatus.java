package services.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EditorStatus {
    TEST_EXISTS("TEST_EXISTS"),
    TEST_NO_NAME("TEST_NO_NAME"),
    EMPTY_QUESTIONS("EMPTY_QUESTIONS"),
    QUESTION_EXISTS("QUESTION_EXISTS"),
    QUESTION_NO_TEXT("QUESTION_NO_TEXT"),
    QUESTION_NO_ANSWERS("QUESTION_NO_ANSWERS"),
    ANSWER_EXISTS("ANSWER_EXISTS"),
    ANSWER_NO_TEXT("ANSWER_NO_TEXT"),
    OK("OK");

    private String type;
}
