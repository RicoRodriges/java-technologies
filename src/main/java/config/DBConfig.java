package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
@PropertySource("classpath:sql_queries.properties")
public class DBConfig {

    @Bean
    public ConnectionPool connectionPool(Environment env) {
        return new ConnectionPool(env.getProperty("h2.url"),
                env.getProperty("h2.user"),
                env.getProperty("h2.password"),
                Integer.parseInt(Objects.requireNonNull(env.getProperty("h2.maxConnection"))));
    }
}
