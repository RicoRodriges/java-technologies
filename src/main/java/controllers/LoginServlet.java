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
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginServlet {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String CATALOG = "/catalog";
    private static final String LOGIN_JSP = "login";
    private static final String FLAG = "flag";
    private static final String LOCALE = "locale";

    private final UserService userService;

    @PostMapping
    protected String doPost(@RequestParam(USER) String name,
                            @RequestParam(PASSWORD) String pass,
                            HttpSession session,
                            Model model) {
        User user = userService.authorizeUser(name, pass);

        String language = (String) session.getAttribute(LOCALE);
        if (language == null) {
            language = "en";
        }
        if (user != null) {
            session.setAttribute(USER, user);
            return "redirect:" + CATALOG;
        } else {
            ResourceBundle r = ResourceBundle.getBundle("internationalization", new Locale(language));
            model.addAttribute(FLAG, r.getString("loginservlet.notfound"));
            return LOGIN_JSP;
        }
    }

    @GetMapping
    protected String doGet() {
        return LOGIN_JSP;
    }
}
