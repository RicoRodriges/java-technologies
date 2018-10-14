package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:sql_queries.properties")
public class DBConfig {

    @Bean
    public DataSource dataSource(Environment env) {
        return new DriverManagerDataSource(env.getProperty("h2.url"),
                env.getProperty("h2.user"),
                env.getProperty("h2.password"));
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
