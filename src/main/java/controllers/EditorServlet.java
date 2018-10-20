package controllers;

import entity.Test;
import entity.TestTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;
import services.impl.EditorStatus;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/editor")
public class EditorServlet {

    private static final String EDITOR_JSP = "editor";
    private static final String TEST = "test";
    private static final String PROBLEM = "problem";
    private static final String ID = "id";
    private static final String TEST_ID = "testId";

    private final TestService testService;

    @PostMapping
    protected String doPost(@RequestParam(TEST) Test editableTest,
                            HttpSession session,
                            Model model) {
        Long testId = (Long) session.getAttribute(TEST_ID);
        if (testId != null) {
            editableTest.setId(testId);
        } else {
            editableTest.setId(null);
        }
        EditorStatus result = changeTest(editableTest);
        if (result == EditorStatus.OK) {
            session.removeAttribute(TEST_ID);
            return "redirect:/catalog";
        } else {
            model.addAttribute(TEST, editableTest);
            model.addAttribute(PROBLEM, result.getType());
            return EDITOR_JSP;
        }
    }

    @GetMapping
    protected String doGet(@RequestParam(name = ID, required = false) Long testID,
                           HttpSession session,
                           Model model) {
        if (testID != null) {
            model.addAttribute(TEST, testService.getTest(testID));
            session.setAttribute(TEST_ID, testID);
        } else {
            Test test = new Test();
            test.setType(TestTypes.MATH);
            model.addAttribute(TEST, test);
            session.removeAttribute(TEST_ID);
        }
        return EDITOR_JSP;
    }

    private EditorStatus changeTest(Test test) {
        return testService.editThroughForm(test);
    }
}
