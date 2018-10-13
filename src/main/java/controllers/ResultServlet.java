package controllers;

import entity.Question;
import entity.Test;
import entity.TestResult;
import entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import services.api.TestResultService;
import services.impl.TestResultServiceImpl;
import services.impl.TestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    private final static Logger log = LogManager.getLogger(ResultServlet.class);
    private static final String RESULT = "result";
    private static final String TEST = "test";
    private static final String SCORE = "score";
    private static final String RESULT_JSP = "result.jsp";
    private static final String TEST_ID = "testId";
    private static final String CATALOG = "/catalog";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        TestServiceImpl testService = new TestServiceImpl();
        Test test = testService.getTest(Long.parseLong(req.getParameter(TEST_ID)));
        Map<Long, List<Long>> answers = new HashMap<>();
        for (Question q : test.getQuest()) {
            if (req.getParameterValues("q" + q.getId()) != null) {
                List<Long> answerList = Arrays.stream(req.getParameterValues("q" + q.getId()))
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .collect(Collectors.toList());
                answers.put(q.getId(), answerList);
            }
        }
        TestResultService testResultService = new TestResultServiceImpl();
        TestResult result = testResultService.CheckTest(test, answers, (User) req.getSession().getAttribute("user"));
        testResultService.add(result);
        int score = testResultService.getScore(result);

        req.setAttribute(RESULT, result);
        req.setAttribute(TEST, test);
        req.setAttribute(SCORE, score);
        req.getRequestDispatcher(RESULT_JSP).forward(req, resp);
        log.info("Test " + test + " was solved. " +
                "Score: " + score + ". " +
                "Correct answers: " + result.getCorrectAnswers() + "/" + result.getCountAnswers() + ".");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect(CATALOG);
    }
}