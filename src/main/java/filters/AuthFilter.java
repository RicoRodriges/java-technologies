package filters;

import entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private static final String FORBIDDEN_JSP = "forbidden.jsp";
    private static final String LOGIN = "login";
    private static final String USER = "user";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        redirect(request, response, chain, getHttpSession((HttpServletRequest) request));
    }

    private void redirect(ServletRequest request, ServletResponse response, FilterChain chain, HttpSession session)
            throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();

        if (isResourcesPages(uri)) {
            chain.doFilter(request, response);
        } else {
            User user;
            if (session != null && (user = (User) session.getAttribute(USER)) != null) {
                if (isTutorPages(uri)) {
                    if (user.getIsTutor()) {
                        chain.doFilter(request, response);
                    } else {
                        ((HttpServletResponse) response).sendRedirect(FORBIDDEN_JSP);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                if (isNotLoginPages(uri)) {
                    ((HttpServletResponse) response).sendRedirect(LOGIN);
                } else {
                    chain.doFilter(request, response);
                }
            }
        }
    }

    private boolean isResourcesPages(String uri) {
        return uri.startsWith("/css")
                || uri.startsWith("/js")
                || uri.startsWith("/images");
    }


    private boolean isNotLoginPages(String uri) {
        return !(uri.endsWith("/login") || uri.endsWith("/registration"));
    }


    private boolean isTutorPages(String uri) {
        return uri.endsWith("/editor")
                || uri.endsWith("/delete")
                || uri.endsWith("/userList");

    }

    private HttpSession getHttpSession(HttpServletRequest request) {
        return request.getSession();
    }

    @Override
    public void destroy() {

    }


}
