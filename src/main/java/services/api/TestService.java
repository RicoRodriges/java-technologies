package services.api;

import dto.TestDto;
import dto.UserDto;
import services.impl.EditorStatus;

import java.util.List;

public interface TestService {

    TestDto getTest(Long id);

    List<TestDto> getAllTests(UserDto userDto);

    void removeTest(Long id);

    EditorStatus editThroughForm(TestDto test);
}