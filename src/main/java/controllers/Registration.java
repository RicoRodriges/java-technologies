package controllers;

import dao.GroupsDAO;
import dao.UniversityDAO;
import dao.UserDAO;
import dto.UserDto;
import entity.GroupEntity;
import entity.University;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.UserService;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
public class Registration {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String REPASSWORD = "passwordRepeat";
    private static final String GROUP = "groups";
    private static final String UNIVERSITY = "univer";
    private static final String REGISTRATION_JSP = "registration";
    private static final String LOGIN_JSP = "login";
    private static final String FLAG = "flag";
    private static final String LOCALE = "locale";

    private final UserService userService;
    private final UserDAO userDAO;
    private final GroupsDAO groupsDAO;
    private final UniversityDAO universityDAO;

    @PostMapping("/registration")
    protected String doPost(@RequestParam(USER) String userNameCred,
                            @RequestParam(PASSWORD) String userPassCred,
                            @RequestParam(REPASSWORD) String userRePassCred,
                            @RequestParam(GROUP) Long groupId,
                            HttpSession session,
                            Model model) {
        String language = (String) session.getAttribute(LOCALE);
        if (language == null) {
            language = "en";
        }
        ResourceBundle r = ResourceBundle.getBundle("internationalization", new Locale(language));

        if (isEmptyFields(userNameCred, userPassCred, userRePassCred)) {
            model.addAttribute(FLAG, r.getString("registrationservlet.fill"));
            return REGISTRATION_JSP;
        } else {
            Optional<GroupEntity> optional = groupsDAO.findById(groupId);
            if (optional.isPresent()) {
                UserDto user = new UserDto(null, userNameCred, userPassCred, false, optional.get());
                if (userService.isAlreadyExists(user)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.exists"));
                    return REGISTRATION_JSP;
                } else if (!userPassCred.equals(userRePassCred)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.wrong"));
                    return REGISTRATION_JSP;
                } else {
                    userService.registerUser(user);
                    return LOGIN_JSP;
                }
            } else {
                model.addAttribute(FLAG, "Bad group");
                return REGISTRATION_JSP;
            }
        }
    }

    @GetMapping("/registration")
    protected String doGet() {
        return REGISTRATION_JSP;
    }

    @GetMapping("/registerTutor")
    protected String doGetTutorPage(Model model) {
        model.addAttribute("universities", universityDAO.findAll());
        return "regTutor";
    }

    @PostMapping("/registerTutor")
    protected String doGetTutorProcess(@RequestParam(USER) String userNameCred,
                                       @RequestParam(PASSWORD) String userPassCred,
                                       @RequestParam(UNIVERSITY) Long univerId) {
        University univer = universityDAO.findById(univerId).get();
        userService.registerUser(new UserDto(null, userNameCred, userPassCred, true, univer.getFaculties().get(0).getDepartments().get(0).getGroups().get(0)));
        return "redirect:/";
    }

    private boolean isEmptyFields(String userNameCred, String userPassCred, String userRePassCred) {
        return userNameCred.isEmpty() || userPassCred.isEmpty() || userRePassCred.isEmpty();
    }
}
