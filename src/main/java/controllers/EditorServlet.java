package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Test;
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
import java.io.IOException;

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
    private final ObjectMapper mapper;

    @PostMapping
    protected String doPost(@RequestParam(TEST) String test,
                            HttpSession session,
                            Model model) throws IOException {
        String testId = String.valueOf(session.getAttribute(TEST_ID));
        Test editableTest = convertJson(test);
        editableTest.setId(Long.parseLong(testId));
        EditorStatus result = changeTest(editableTest);
        if (result == EditorStatus.OK) {
            session.setAttribute(TEST_ID, null);
            return "redirect:/catalog";
        } else {
            model.addAttribute(TEST, editableTest);
            model.addAttribute(PROBLEM, result.getType());
            return EDITOR_JSP;
        }
    }

    @GetMapping
    protected String doGet(@RequestParam(ID) String testIdReq,
                           HttpSession session,
                           Model model) {
        Long testID = Long.parseLong(testIdReq);
        Test test = testService.getTest(testID);
        model.addAttribute(TEST, test);
        session.setAttribute(TEST_ID, testID);
        return EDITOR_JSP;
    }

    private Test convertJson(String test) throws IOException {
        return mapper.readValue(test, Test.class);
    }

    private EditorStatus changeTest(Test test) {
        return testService.editThroughForm(test);
    }
}
