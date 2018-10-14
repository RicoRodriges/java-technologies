package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;
import services.impl.EditorStatus;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/addTest")
public class AddTestServlet {
    private static final String TEST = "test";
    private static final String PROBLEM = "problem";

    private final TestService testService;
    private final ObjectMapper mapper;

    @PostMapping
    protected String doPost(@RequestParam(TEST) String test, Model model) throws IOException {
        Test newTest = convertJson(test);
        EditorStatus result = createNewTest(newTest);
        if (result == EditorStatus.OK) {
            return "redirect:/catalog";
        } else {
            newTest.setId(-1);
            model.addAttribute(TEST, newTest);
            model.addAttribute(PROBLEM, result.getType());
            return "editor";
        }
    }

    private Test convertJson(String test) throws IOException {
        return mapper.readValue(test, Test.class);
    }

    private EditorStatus createNewTest(Test test) {
        return testService.addTestFromForm(test);
    }
}
