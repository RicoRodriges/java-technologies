package controllers;

import entity.User;
import services.api.UserService;
import services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/registrationServlet")
public class RegistrationServlet extends HttpServlet {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String REPASSWORD = "passwordRepeat";
    private static final UserService userService = new UserServiceImpl();
    private static final String REGISTRATION_JSP = "/registration.jsp";
    private static final String LOGIN_JSP = "/login.jsp";
    private static final String FLAG = "flag";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String userNameCred = req.getParameter(USER);
        String userPassCred = req.getParameter(PASSWORD);
        String userRePassCred = req.getParameter(REPASSWORD);
        HttpSession session = req.getSession();
        Locale locale;

        String language = (String) session.getAttribute("locale");
        if(language == null) {
            language = "en";
        }
        req.setAttribute("locale", language);
        locale = new Locale(language);
        ResourceBundle r = ResourceBundle.getBundle("internationalization", locale);

        if (isEmptyFields(userNameCred, userPassCred, userRePassCred)) {
            req.setAttribute(FLAG, r.getString("registrationservlet.fill"));
            req.getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
        } else {
            User user = new User(userNameCred, userPassCred, false);


            if (userService.isAlreadyExists(user)) {
                req.setAttribute(FLAG, r.getString("registrationservlet.exists"));
                req.getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
            } else if (!userPassCred.equals(userRePassCred)) {
                req.setAttribute(FLAG, r.getString("registrationservlet.wrong"));
                req.getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
            } else {
                userService.registerUser(user);
                req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(REGISTRATION_JSP).forward(req, resp);
    }

    private boolean isEmptyFields(String userNameCred, String userPassCred, String userRePassCred) {
        return userNameCred.isEmpty() || userPassCred.isEmpty() || userRePassCred.isEmpty();
    }
}
