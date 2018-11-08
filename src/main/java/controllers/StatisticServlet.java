package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import config.security.SpringUser;
import dao.TestResultDAO;
import dto.UserDto;
import entity.TestResult;
import entity.University;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stat")
@RequiredArgsConstructor
public class StatisticServlet {

    private static final String STAT_JSP = "stat";

    private final TestResultDAO testResultDAO;
    private final ObjectMapper objectMapper;

    @GetMapping
    protected String doGet(Model model) {
        SpringUser springUser = (SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = springUser.getUser();
        University university = user.getUniversity();
        if (university == null) {
            return "redirect:/catalog";
        }
        List<TestResult> results = testResultDAO.findAll().stream()
                .filter(r -> r.getUser().getGroup().getDepartment().getFaculty().getUniversity().getId().equals(university.getId()))
                .collect(Collectors.toList());
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (TestResult result : results) {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("test", result.getTest().getName());
            objectNode.put("user", result.getUser().getName());
            objectNode.put("univer", university.getName());
            objectNode.put("fac", result.getUser().getGroup().getDepartment().getFaculty().getName());
            objectNode.put("dep", result.getUser().getGroup().getDepartment().getName());
            objectNode.put("group", result.getUser().getGroup().getName());
            objectNode.put("result", result.getCorrectAnswers() + " / " + result.getCountAnswers());
            objectNode.put("perc", (100 * result.getCorrectAnswers() / result.getCountAnswers()) + " %");
            objectNode.put("date", result.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            arrayNode.add(objectNode);
        }
        model.addAttribute("jsonData", arrayNode.toString());
        return STAT_JSP;
    }
}

