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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String CATALOG = "/catalog";
    private static final String LOGIN_JSP = "login.jsp";
    private static final String FLAG = "flag";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String credentialsUser = req.getParameter(USER);
        String credentialsPassword = req.getParameter(PASSWORD);

        authorizeUser(req,
                resp,
                new UserServiceImpl(),
                credentialsUser,
                credentialsPassword);


    }

    private void authorizeUser(HttpServletRequest req, HttpServletResponse resp, UserService validator, String name, String pass)
            throws ServletException, IOException {

        User user = validator.authorizeUser(name, pass);
        HttpSession session = req.getSession();
        Locale locale;

        String language = (String) session.getAttribute("locale");
        if (language == null) {
            language = "en";
        }
        req.setAttribute("locale", language);
        locale = new Locale(language);
        ResourceBundle r = ResourceBundle.getBundle("internationalization", locale);
        if (user != null) {

            session.setAttribute(USER, user);

            resp.sendRedirect(CATALOG);

        } else {
            //session.setAttribute(FLAG, "Login/email combination not found");
            req.setAttribute(FLAG, r.getString("loginservlet.notfound"));

            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);

    }
}
