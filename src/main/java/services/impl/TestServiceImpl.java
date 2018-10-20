package services.impl;

import dao.TestDAO;
import dto.AnswerDto;
import dto.QuestionDto;
import dto.TestDto;
import dto.TestTypes;
import entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.api.TestService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private final TestDAO testDAO;

    @Override
    public TestDto getTest(Long id) {
        Test test = testDAO.findById(id);
        if (test == null) {
            return null;
        }
        return new TestDto(test);
    }

    @Override
    public List<TestDto> getAllTests() {
        List<Test> tests = testDAO.findAll();
        ArrayList<TestDto> testDtos = new ArrayList<>(tests.size());
        for (Test test : tests) {
            testDtos.add(new TestDto(test));
        }
        return testDtos;
    }

    @Override
    public List<TestDto> getAllTestsByTheme(TestTypes type) {
        List<Test> tests = testDAO.findAllByType(type);
        ArrayList<TestDto> testDtos = new ArrayList<>(tests.size());
        for (Test test : tests) {
            testDtos.add(new TestDto(test));
        }
        return testDtos;
    }

    @Override
    public void removeTest(Long id) {
        testDAO.deleteById(id);
    }

    @Override
    public EditorStatus editThroughForm(TestDto test) {
        EditorStatus checkResult = checkTest(test);
        if (checkResult.equals(EditorStatus.OK)) {
            test.setCreationDate(LocalDate.now());
            testDAO.save(new Test(test));
        }
        return checkResult;
    }

    private EditorStatus checkTest(TestDto test) {
        if (test.getName().isEmpty()) {
            return EditorStatus.TEST_NO_NAME;
        }
        if (test.getQuest().isEmpty()) {
            return EditorStatus.EMPTY_QUESTIONS;
        }
        for (QuestionDto quest : test.getQuest()) {
            if (quest.getText().isEmpty()) {
                return EditorStatus.QUESTION_NO_TEXT;
            }
            if (quest.getAnswers().isEmpty()) {
                return EditorStatus.QUESTION_NO_ANSWERS;
            } else {
                for (AnswerDto answer : quest.getAnswers()) {
                    if (answer.getText().isEmpty()) {
                        return EditorStatus.ANSWER_NO_TEXT;
                    }
                }
            }
        }
        return EditorStatus.OK;
    }
}
