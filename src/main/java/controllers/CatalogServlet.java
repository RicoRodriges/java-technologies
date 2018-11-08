package controllers;

import config.security.SpringUser;
import dto.TestDto;
import dto.TestTypes;
import dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    protected String doGet(Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("tests", testService.getAllTests(user));
        return "catalog";
    }

    @PostMapping
    protected String doPost(Model model) {
        return doGet(model);
    }
}
