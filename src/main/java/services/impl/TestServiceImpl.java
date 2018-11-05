package services.impl;

import dao.TestDAO;
import dto.*;
import entity.GroupEntity;
import entity.Test;
import entity.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.api.TestService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private final TestDAO testDAO;

    @Override
    public TestDto getTest(Long id) {
        return testDAO.findById(id)
                .map(TestDto::new)
                .orElse(null);
    }

    @Override
    public List<TestDto> getAllTests(UserDto userDto) {
        Set<Test> tests;
        if (!userDto.getIsTutor()) {
            GroupEntity group = userDto.getGroupEntity();
            tests = testDAO.findAllByGroupsIn(Collections.singletonList(group));
        } else if (userDto.getGroupEntity() == null) {
            tests = new HashSet<>(testDAO.findAll());
        } else {
            List<GroupEntity> groups = userDto.getGroupEntity().getDepartment().getFaculty().getUniversity().getFaculties().stream()
                    .flatMap(f -> f.getDepartments().stream()
                            .flatMap(d -> d.getGroups().stream())).collect(Collectors.toList());
            tests = testDAO.findAllByGroupsIn(groups);
        }
        ArrayList<TestDto> testDtos = new ArrayList<>(tests.size());
        for (Test test : tests) {
            testDtos.add(new TestDto(test));
        }
        return testDtos;
    }

    @Override
    public List<TestDto> getAllTestsByTheme(TestTypes type, UserDto userDto) {
        List<TestDto> tests = getAllTests(userDto);
        tests.removeIf(t -> !t.getType().equals(type));
        return tests;
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
