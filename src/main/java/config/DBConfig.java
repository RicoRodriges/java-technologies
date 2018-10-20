package config;

import entity.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.util.Properties;

@Configuration
@PropertySource("classpath:sql_queries.properties")
public class DBConfig {

    @Bean
    public SessionFactory sessionFactory(Environment env) {
        Properties prop = new Properties();
        prop.setProperty(org.hibernate.cfg.Environment.DRIVER, env.getProperty("hibernate.driver"));
        prop.setProperty(org.hibernate.cfg.Environment.URL, env.getProperty("hibernate.db.url"));
        prop.setProperty(org.hibernate.cfg.Environment.USER, env.getProperty("hibernate.db.user"));
        prop.setProperty(org.hibernate.cfg.Environment.PASS, env.getProperty("hibernate.db.password"));
        prop.setProperty(org.hibernate.cfg.Environment.DIALECT, env.getProperty("hibernate.dialect"));
        prop.setProperty(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        return configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Test.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Answer.class)
                .addAnnotatedClass(TestResult.class)
                .addProperties(prop)
                .buildSessionFactory();
    }

    @Bean(destroyMethod = "close")
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public EntityManager entityManager(SessionFactory sessionFactory) {
        return sessionFactory.openSession();
    }

    @Bean
    public InitDB initDB(SessionFactory sessionFactory, Environment env) {
        return new InitDB(sessionFactory,
                env.getProperty("user.tutor.name"),
                env.getProperty("user.tutor.password"));
    }
}
