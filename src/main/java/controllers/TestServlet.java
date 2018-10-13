package controllers;

import entity.Test;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import services.api.TestService;
import services.impl.TestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

    private static final String TEST_JSP = "/test.jsp";
    private static final String TEST = "test";
    private static final String ID = "id";
    private static final Logger log = LogManager.getLogger(TestServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter(ID);
        TestService testService = new TestServiceImpl();
        Test particularTest = testService.getTest(Long.parseLong(id));         
        req.setAttribute(TEST, particularTest);
        req.getRequestDispatcher(TEST_JSP).forward(req, resp);
        log.info("Test " + particularTest + " is solving by " + req.getSession().getAttribute("user"));
    }
}
