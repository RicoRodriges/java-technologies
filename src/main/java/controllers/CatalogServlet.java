package controllers;

import dto.TestDto;
import dto.TestTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.TestService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping({"/catalog", "/"})
@RequiredArgsConstructor
public class CatalogServlet {

    private final TestService testService;

    @GetMapping
    protected String doGet(@RequestParam(name = "theme", required = false) String theme, Model model) {
        List<TestDto> tests = Collections.emptyList();
        if (theme == null || "All".equals(theme)) {
            tests = testService.getAllTests();
            model.addAttribute("theme", "All");
        } else {
            for (TestTypes type : TestTypes.values()) {
                if (type.getName().equals(theme)) {
                    tests = testService.getAllTestsByTheme(type);
                    break;
                }
            }
            model.addAttribute("theme", theme);
        }
        model.addAttribute("tests", tests);
        return "catalog";
    }

    @PostMapping
    protected String doPost(@RequestParam(name = "theme", required = false) String theme, Model model) {
        return doGet(theme, model);
    }
}
