package controllers;

import entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestServlet {

    private static final String TEST_JSP = "test";
    private static final String TEST = "test";
    private static final String ID = "id";

    private final TestService testService;

    @GetMapping
    protected String doGet(@RequestParam(ID) Long testId, Model model) {
        Test particularTest = testService.getTest(testId);
        model.addAttribute(TEST, particularTest);
        return TEST_JSP;
    }
}
