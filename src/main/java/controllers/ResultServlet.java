package controllers;

import config.security.SpringUser;
import dto.QuestionDto;
import dto.TestDto;
import dto.TestResultDto;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.api.TestResultService;
import services.api.TestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultServlet {

    private final static Logger log = LogManager.getLogger(ResultServlet.class);
    private static final String RESULT = "result";
    private static final String TEST = "test";
    private static final String SCORE = "score";
    private static final String RESULT_JSP = "result";
    private static final String TEST_ID = "testId";
    private static final String CATALOG = "/catalog";

    private final TestService testService;
    private final TestResultService testResultService;

    @PostMapping
    protected String doPost(HttpServletRequest req, Model model) {
        TestDto test = testService.getTest(Long.parseLong(req.getParameter(TEST_ID)));
        Map<Long, List<Long>> answers = new HashMap<>();
        for (QuestionDto q : test.getQuest()) {
            if (req.getParameterValues("q" + q.getId()) != null) {
                List<Long> answerList = Arrays.stream(req.getParameterValues("q" + q.getId()))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .collect(Collectors.toList());
                answers.put(q.getId(), answerList);
            }
        }
        SpringUser springUser = (SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TestResultDto result = testResultService.CheckTest(test, answers, springUser.getUser());
        testResultService.add(result);
        int score = testResultService.getScore(result);

        log.info("TestDto " + test + " was solved. " +
                "Score: " + score + ". " +
                "Correct answers: " + result.getCorrectAnswers() + "/" + result.getCountAnswers() + ".");

        model.addAttribute(RESULT, result);
        model.addAttribute(TEST, test);
        model.addAttribute(SCORE, score);
        return RESULT_JSP;
    }

    @GetMapping
    protected String doGet() {
        return "redirect:" + CATALOG;
    }
}