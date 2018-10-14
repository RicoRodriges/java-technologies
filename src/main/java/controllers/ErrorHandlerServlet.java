package controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RequestMapping("/errors")
public class ErrorHandlerServlet {

    private final static Logger log = LogManager.getLogger(ErrorHandlerServlet.class);
    private static final String STATUS_CODE = "statusCode";
    private static final String SERVLET_NAME = "servletName";
    private static final String REQUEST_URI = "requestUri";
    private static final String EXCEPTION_TYPE = "exceptionType";
    private static final String EXCEPTION_MESSAGE = "exceptionMessage";
    private static final String ERROR_HANDLER_JSP = "errorPage";

    @GetMapping
    public String doGet(HttpServletRequest req, Model model) {
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

        model.addAttribute(STATUS_CODE, statusCode);
        model.addAttribute(SERVLET_NAME, servletName);
        model.addAttribute(REQUEST_URI, requestUri);
        model.addAttribute(EXCEPTION_TYPE, exceptionType);
        model.addAttribute(EXCEPTION_MESSAGE, exceptionMessage);

        return ERROR_HANDLER_JSP;
    }

    @PostMapping
    protected String doPost(HttpServletRequest req, Model model) {
        return doGet(req, model);
    }
}
