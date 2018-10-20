package services.api;

import dto.TestDto;
import dto.TestTypes;
import services.impl.EditorStatus;

import java.util.List;

public interface TestService {

    TestDto getTest(Long id);

    List<TestDto> getAllTests();

    List<TestDto> getAllTestsByTheme(TestTypes type);

    void removeTest(Long id);

    EditorStatus editThroughForm(TestDto test);
}