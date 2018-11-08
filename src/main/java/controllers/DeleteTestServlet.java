package controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delete")
public class DeleteTestServlet {

    private static final String ID = "id";

    private final TestService testService;

    @GetMapping
    protected String doPost(@RequestParam(ID) Long testId) {
        testService.removeTest(testId);
        return "redirect:/catalog";
    }
}
