package controllers;

import services.api.TestService;
import services.impl.TestServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class deleteTestServlet extends HttpServlet {

    private static final String ID = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        TestService testService = new TestServiceImpl();

        String credentialsTestId = req.getParameter(ID);

        testService.removeTest(Long.parseLong(credentialsTestId));
        resp.sendRedirect("/catalog");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}
