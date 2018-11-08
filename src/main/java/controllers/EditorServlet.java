package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.security.SpringUser;
import dao.GroupDAO;
import dao.UniversityDAO;
import dto.TestDto;
import dto.TestTypes;
import dto.UserDto;
import entity.GroupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;
import services.impl.EditorStatus;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static config.Utils.getAvailableGroups;

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
    private final GroupDAO groupsDAO;
    private final UniversityDAO universityDAO;
    private final ObjectMapper objectMapper;

    @PostMapping
    @Transactional
    protected String doPost(@RequestParam(TEST) TestDto editableTest,
                            HttpSession session,
                            Model model) throws IOException {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<GroupEntity> groups = getAvailableGroups(user, universityDAO, groupsDAO);
        model.addAttribute("groups", objectMapper.writeValueAsString(groups));
        editableTest.setGroups(editableTest.getGroups().stream()
                .map(groupEntity -> groupsDAO.findById(groupEntity.getId()).orElse(null))
                .collect(Collectors.toList()));
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
                           Model model) throws JsonProcessingException {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<GroupEntity> groups = getAvailableGroups(user, universityDAO, groupsDAO);
        model.addAttribute("groups", objectMapper.writeValueAsString(groups));
        if (testID != null) {
            TestDto test = testService.getTest(testID);
            model.addAttribute(TEST, test);
            session.setAttribute(TEST_ID, testID);
            model.addAttribute("selGroups", objectMapper.writeValueAsString(test.getGroups()));
        } else {
            TestDto test = new TestDto();
            test.setType(TestTypes.MATH);
            model.addAttribute(TEST, test);
            session.removeAttribute(TEST_ID);
            model.addAttribute("selGroups", "[]");
        }
        return EDITOR_JSP;
    }

    private EditorStatus changeTest(TestDto test) {
        return testService.editThroughForm(test);
    }
}
