package config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class which configures the ServletContext in Servlet 3.0+ environments programmatically -
 * as opposed to (or possibly in conjunction with) the traditional web.xml-based approach.
 * This class is automatically detected by SpringServletContainerInitializer,
 * which itself is bootstrapped automatically by any Servlet 3.0 container.
 * See its <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/SpringServletContainerInitializer.html">
 * Javadoc</a> for details on this bootstrapping mechanism.
 */
public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringMVCConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}
