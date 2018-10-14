package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logoutServlet")
public class LogoutServlet {

    private static final String LOGIN_JSP = "login";

    @PostMapping
    protected String doPost(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return LOGIN_JSP;
    }

    @GetMapping
    protected String doGet(HttpSession session) {
        return doPost(session);
    }
}
