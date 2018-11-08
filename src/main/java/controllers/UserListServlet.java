package controllers;

import config.security.SpringUser;
import dao.UniversityDAO;
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

import static config.Utils.getAvailableGroups;

@Controller
@RequestMapping("/userList")
@RequiredArgsConstructor
public class UserListServlet {

    private static final String USER_LIST_JSP = "userList";

    private final UserService userService;
    private final UniversityDAO universityDAO;

    @GetMapping
    protected String doGet(Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<UserDto> users = userService.getAll();
        if (user.getUniversity() != null) {
            Set<Long> groups = getAvailableGroups(user, universityDAO, null).stream()
                    .map(GroupEntity::getId)
                    .collect(Collectors.toSet());
            users = users.stream()
                    .filter(u -> u.getGroupEntity() != null && groups.contains(u.getGroupEntity().getId()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("users", users);
        return USER_LIST_JSP;
    }
}