package services.api;

import entity.Test;
import services.impl.EditorStatus;

import java.util.List;

public interface TestService {


    Test addTest(Test test);

    Test getTest(Long id);

    List<Test> getAllTests();

    List<Test> getAllTestsByTheme(String theme);

    void removeTest(Long id);

    void updateTest(Test test);

    EditorStatus addTestFromForm(Test test);

    EditorStatus editThroughForm(Test test);
}