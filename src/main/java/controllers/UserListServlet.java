package controllers;

import config.security.SpringUser;
import dto.UserDto;
import entity.GroupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import services.api.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/userList")
@RequiredArgsConstructor
public class UserListServlet {

    private static final String USER_LIST_JSP = "userList";

    private final UserService userService;

    @GetMapping
    protected String doGet(Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<UserDto> users = userService.getAll();
        if (user.getGroupEntity() != null) {
            Set<Long> groups = user.getGroupEntity().getDepartment().getFaculty().getUniversity().getFaculties().stream()
                    .flatMap(f -> f.getDepartments().stream().flatMap(
                            d -> d.getGroups().stream().map(GroupEntity::getId)
                    )).collect(Collectors.toSet());
            users = users.stream()
                    .filter(u -> u.getGroupEntity() != null && groups.contains(u.getGroupEntity().getId()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("users", users);
        return USER_LIST_JSP;
    }
}