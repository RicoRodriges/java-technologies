package controllers;

import config.security.SpringUser;
import dto.TestResultDto;
import dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestResultService;
import services.api.UserService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileServlet {

    private static final String PROFILE_JSP = "profile";
    private static final String USER = "user";

    private final UserService userService;
    private final TestResultService testResultService;

    @GetMapping
    protected String doGet(@RequestParam(name = USER, required = false) String username,
                           Model model) {
        SpringUser springUser = (SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = springUser.getUser();
        if (username == null) {
            return displayUserTests(user, model);
        } else {
            if (user.getIsTutor()) {
                return displayUserTests(userService.get(username), model);
            } else {
                return "forbidden";
            }
        }
    }

    private String displayUserTests(UserDto user, Model model) {
        List<TestResultDto> testResults = testResultService.getAllTestResultsByUserId(user.getId());
        Collections.reverse(testResults);
        model.addAttribute("testResults", testResults);
        return PROFILE_JSP;
    }
}

