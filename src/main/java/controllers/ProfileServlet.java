package controllers;

import entity.TestResult;
import entity.User;
import services.impl.TestResultServiceImpl;
import services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/profileServlet")
public class ProfileServlet extends HttpServlet {

    private static final String PROFILE_JSP = "/profile.jsp";
    private static final String USER = "user";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(PROFILE_JSP).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user;
        if (req.getParameter(USER) == null) {
            user = (User) session.getAttribute(USER);
            doPostTestResults(req, resp, user);
        } else {
            if (((User) session.getAttribute(USER)).getTutor()) {
                user = new UserServiceImpl().get(req.getParameter(USER));
                doPostTestResults(req, resp, user);
            } else {
                req.getRequestDispatcher("forbidden.jsp").forward(req, resp);
            }
        }
    }

    private void doPostTestResults(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<TestResult> testResults = new TestResultServiceImpl()
                .getAllTestResultsByUserId(user.getId());
        Collections.reverse(testResults);
        req.setAttribute("testResults", testResults);
        doPost(req, resp);
    }
}

