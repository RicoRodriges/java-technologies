package controllers;

import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.api.UserService;

import java.util.List;

@Controller
@RequestMapping("/userListServlet")
@RequiredArgsConstructor
public class UserListServlet {

    private static final String USER_LIST_JSP = "userList";

    private final UserService userService;

    @PostMapping
    protected String doPost(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return USER_LIST_JSP;
    }

    @GetMapping
    protected String doGet(Model model) {
        return doPost(model);
    }
}