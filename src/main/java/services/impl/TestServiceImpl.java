package services.impl;

import dao.TestDAO;
import entity.Answer;
import entity.Question;
import entity.Test;
import entity.TestTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import services.api.TestService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDAO testDAO;

    @Override
    public Test getTest(Long id) {
        return testDAO.findById(id);
    }

    @Override
    public List<Test> getAllTests() {
        return testDAO.findAll();
    }

    @Override
    public List<Test> getAllTestsByTheme(TestTypes type) {
        return testDAO.findAllByType(type);
    }

    @Override
    public void removeTest(Long id) {
        testDAO.deleteById(id);
    }

    @Override
    public EditorStatus editThroughForm(Test test) {
        EditorStatus checkResult = checkTest(test);
        if (checkResult.equals(EditorStatus.OK)) {
            test.setCreationDate(LocalDate.now());
            testDAO.save(test);
        }
        return checkResult;
    }

    private EditorStatus checkTest(Test test) {
        if (test.getName().isEmpty()) {
            return EditorStatus.TEST_NO_NAME;
        }
        if (test.getQuest().isEmpty()) {
            return EditorStatus.EMPTY_QUESTIONS;
        }
        for (Question quest : test.getQuest()) {
            if (quest.getText().isEmpty()) {
                return EditorStatus.QUESTION_NO_TEXT;
            }
            if (quest.getAnswers().isEmpty()) {
                return EditorStatus.QUESTION_NO_ANSWERS;
            } else {
                for (Answer answer : quest.getAnswers()) {
                    if (answer.getText().isEmpty()) {
                        return EditorStatus.ANSWER_NO_TEXT;
                    }
                }
            }
        }
        return EditorStatus.OK;
    }
}
