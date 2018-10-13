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
import java.io.IOException;

@WebServlet("/addTest")
public class AddTestServlet extends HttpServlet {

    private static final String TEST = "test";
    private static final TestService testService = new TestServiceImpl();
    private static final String PROBLEM = "problem";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String test = req.getParameter(TEST);
        Test newTest = convertJson(test);
        EditorStatus result = createNewTest(newTest);
        if(result == EditorStatus.OK){
            resp.sendRedirect("/catalog");
        } else {
            newTest.setId(-1);
            req.setAttribute(TEST, newTest);
            req.setAttribute(PROBLEM, result.getType());
            req.getRequestDispatcher("/editor.jsp").forward(req, resp);
        }
    }

    private Test convertJson(String test) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(test, Test.class);
    }

    private EditorStatus createNewTest(Test test) {
        return testService.addTestFromForm(test);
    }
}
