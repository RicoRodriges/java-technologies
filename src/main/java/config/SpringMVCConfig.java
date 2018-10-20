package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import converter.TestConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan({"config", "controllers", "services"})
@EnableWebMvc
public class SpringMVCConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/", ".jsp").viewClass(JstlView.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/*").addResourceLocations("/css/");
        registry.addResourceHandler("/images/*").addResourceLocations("/images/");
        registry.addResourceHandler("/js/*").addResourceLocations("/js/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TestConverter(objectMapper()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
