package controllers;

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
import java.util.ResourceBundle;

@Controller
@RequestMapping("/registrationServlet")
@RequiredArgsConstructor
public class RegistrationServlet {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String REPASSWORD = "passwordRepeat";
    private static final String REGISTRATION_JSP = "registration";
    private static final String LOGIN_JSP = "login";
    private static final String FLAG = "flag";

    private final UserService userService;

    @PostMapping
    protected String doPost(@RequestParam(USER) String userNameCred,
                            @RequestParam(PASSWORD) String userPassCred,
                            @RequestParam(REPASSWORD) String userRePassCred,
                            HttpSession session,
                            Model model) {
        String language = (String) session.getAttribute("locale");
        if (language == null) {
            language = "en";
        }
        model.addAttribute("locale", language);
        Locale locale = new Locale(language);
        ResourceBundle r = ResourceBundle.getBundle("internationalization", locale);

        if (isEmptyFields(userNameCred, userPassCred, userRePassCred)) {
            model.addAttribute(FLAG, r.getString("registrationservlet.fill"));
            return REGISTRATION_JSP;
        } else {
            User user = new User(userNameCred, userPassCred, false);
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
        }
    }

    @GetMapping
    protected String doGet() {
        return REGISTRATION_JSP;
    }

    private boolean isEmptyFields(String userNameCred, String userPassCred, String userRePassCred) {
        return userNameCred.isEmpty() || userPassCred.isEmpty() || userRePassCred.isEmpty();
    }
}
