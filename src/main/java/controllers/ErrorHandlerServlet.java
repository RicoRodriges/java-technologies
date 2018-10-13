package controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/ErrorHandler", "/parts/*"}, name = "ErrorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    private final static Logger log = LogManager.getLogger(ErrorHandlerServlet.class);
    private static final String STATUS_CODE = "statusCode";
    private static final String SERVLET_NAME = "servletName";
    private static final String REQUEST_URI = "requestUri";
    private static final String EXCEPTION_TYPE = "exceptionType";
    private static final String EXCEPTION_MESSAGE = "exceptionMessage";
    private static final String ERROR_HANDLER_JSP = "/errorPage.jsp";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        String exceptionType = null;
        String exceptionMessage = null;
        StringBuilder logMessage = new StringBuilder("\nError: ");

        if (throwable != null) {
            exceptionType = throwable.getClass().getName();
            exceptionMessage = throwable.getMessage();
        }

        if (servletName == null) {
            servletName = "Unknown";
        }

        if (requestUri == null) {
            requestUri = "Unknown";
        }

        logMessage
                .append(statusCode)
                .append("\nRequest URI: ")
                .append(requestUri)
                .append("\nException type: ")
                .append(exceptionType)
                .append("\nException message: ")
                .append(exceptionMessage);
        log.info(logMessage);

        req.setAttribute(STATUS_CODE, statusCode);
        req.setAttribute(SERVLET_NAME, servletName);
        req.setAttribute(REQUEST_URI, requestUri);
        req.setAttribute(EXCEPTION_TYPE, exceptionType);
        req.setAttribute(EXCEPTION_MESSAGE, exceptionMessage);

        req.getRequestDispatcher(ERROR_HANDLER_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        doGet(req, resp);
    }
}
