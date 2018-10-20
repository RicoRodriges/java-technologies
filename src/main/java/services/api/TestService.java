package services.api;

import entity.Test;
import entity.TestTypes;
import services.impl.EditorStatus;

import java.util.List;

public interface TestService {

    Test getTest(Long id);

    List<Test> getAllTests();

    List<Test> getAllTestsByTheme(TestTypes type);

    void removeTest(Long id);

    EditorStatus editThroughForm(Test test);
}