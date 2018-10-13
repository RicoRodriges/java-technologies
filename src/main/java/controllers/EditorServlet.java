package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Test;
import services.api.TestService;
import services.impl.EditorStatus;
import services.impl.TestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editor")
public class EditorServlet extends HttpServlet {

    private static final String EDITOR_JSP = "editor.jsp";
    private static final String TEST = "test";
    private static final String PROBLEM = "problem";
    private static final String ID = "id";
    private static final String TEST_ID = "testId";
    private static final TestService testService = new TestServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String testId = String.valueOf(session.getAttribute(TEST_ID));
        String test = req.getParameter(TEST);
        Test editableTest = convertJson(test);
        editableTest.setId(Long.parseLong(testId));
        EditorStatus result = changeTest(editableTest);
        if(result == EditorStatus.OK){
            session.setAttribute(TEST_ID, null);
            resp.sendRedirect("/catalog");
        } else {
            req.setAttribute(TEST, editableTest);
            req.setAttribute(PROBLEM, result.getType());
            req.getRequestDispatcher(EDITOR_JSP).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        TestService testService = new TestServiceImpl();
        Long testID = Long.parseLong(req.getParameter(ID));
        Test test = testService.getTest(testID);
        req.setAttribute(TEST, test);
        session.setAttribute(TEST_ID, testID);
        req.getRequestDispatcher(EDITOR_JSP).forward(req, resp);
    }

    private Test convertJson(String test) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(test, Test.class);
    }

    private EditorStatus changeTest(Test test) {
        return testService.editThroughForm(test);
    }
}
