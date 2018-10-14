package controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delete")
public class deleteTestServlet {

    private static final String ID = "id";

    private final TestService testService;

    @PostMapping
    protected String doPost(@RequestParam(ID) String credentialsTestId) {
        testService.removeTest(Long.parseLong(credentialsTestId));
        return "redirect:/catalog";
    }
}
